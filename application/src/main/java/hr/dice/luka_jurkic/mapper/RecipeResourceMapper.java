package hr.dice.luka_jurkic.mapper;

import hr.dice.luka_jurkic.persistence.entity.IngredientUnit;
import hr.dice.luka_jurkic.rest.dto.ImportedIngredientDTO;
import hr.dice.luka_jurkic.rest.dto.ImportedRecipeDTO;
import hr.dice.luka_jurkic.rest.dto.ImportedStepDTO;
import hr.dice.luka_jurkic.rest.resources.RecipeResource;
import hr.dice.luka_jurkic.utils.StringToDoubleConverter;
import hr.dice.luka_jurkic.utils.UnitConverter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Mapper(componentModel = "spring")
public interface RecipeResourceMapper {

    @Mapping(source = "title", target = "name")
    @Mapping(source = "instructions", target = "importedSteps", qualifiedByName = "instructionsToImportedSteps")
    @Mapping(source = "ingredients", target = "importedIngredients", qualifiedByName = "stringIngredientsToImportedIngredients")
    ImportedRecipeDTO resourceToDto(RecipeResource recipeResource);

    List<ImportedRecipeDTO> resourceToDto(List<RecipeResource> resources);

    @Named("convertToLocalUnits")
    default void convertToLocalUnits(List<ImportedRecipeDTO> recipes, List<String> units) {
        units = units.stream().map(String::toLowerCase).toList();
        for (ImportedRecipeDTO recipe : recipes) {
            for(ImportedIngredientDTO ingredient : recipe.getImportedIngredients()) {
                String ingredientUnit = ingredient.getUnit().toLowerCase();
                if(!units.contains(ingredientUnit)){
                    switch (ingredientUnit){
                        case "cup", "c": {
                            ingredient.setUnit(IngredientUnit.G.toString());
                            ingredient.setAmount(UnitConverter.cupToMl(
                                    StringToDoubleConverter.convertStringWithSlashToDouble(ingredient.getAmount())
                            ).toString());
                            break;
                        }
                        case "pk": {
                            ingredient.setUnit(IngredientUnit.L.toString());
                            ingredient.setAmount(UnitConverter.peckToL(
                                    StringToDoubleConverter.convertStringWithSlashToDouble(ingredient.getAmount())
                            ).toString());
                            break;
                        }
                        case "oz", "ounce": {
                            ingredient.setUnit(IngredientUnit.G.toString());
                            ingredient.setAmount(UnitConverter.ounceToG(
                                    StringToDoubleConverter.convertStringWithSlashToDouble(ingredient.getAmount())
                            ).toString());
                            break;
                        }
                        case "lb", "pound": {
                            ingredient.setUnit(IngredientUnit.KG.toString());
                            ingredient.setAmount(UnitConverter.poundToKg(
                                    StringToDoubleConverter.convertStringWithSlashToDouble(ingredient.getAmount())
                            ).toString());
                            break;
                        }
                        case "tb": {
                            ingredient.setUnit(IngredientUnit.TBSP.toString());
                            break;
                        }
                        case "ts", "cl": {
                            ingredient.setUnit(IngredientUnit.TSP.toString());
                            break;
                        }
                        case "sm": {
                            ingredient.setUnit(IngredientUnit.PIECE.toString());
                            break;
                        }
                        case "sl": {
                            ingredient.setUnit(IngredientUnit.SLICE.toString());
                            break;
                        }
                    }
                }
            }
        }
    }

    @Named("instructionsToImportedSteps")
    static List<ImportedStepDTO> instructionsToImportedSteps(String instructions) {
        List<ImportedStepDTO> importedSteps = new ArrayList<>();
        int number = 1;
        for (String step : instructions.split("\\.")) {
            ImportedStepDTO importedStep = new ImportedStepDTO();
            importedStep.setNumber(number++);
            importedStep.setDescription(step);
            importedSteps.add(importedStep);
        }
        return importedSteps;
    }

    @Named("stringIngredientsToImportedIngredients")
    static List<ImportedIngredientDTO> stringIngredientsToImportedIngredients(String ingredients) {
        List<ImportedIngredientDTO> importedIngredients = new ArrayList<>();
        for (String ingredient : ingredients.split("\\|")) {
            StringBuilder name = new StringBuilder();
            StringBuilder amount = new StringBuilder();
            String unit = "";
            ingredient = ingredient.split(",")[0];
            ingredient = ingredient.split(";")[0];
            if(ingredient.contains("(") || ingredient.contains(")"))
                ingredient = ingredient.substring(0, ingredient.indexOf("(")) + ingredient.substring(ingredient.indexOf(")")+1);

            if(ingredient.contains("can") || ingredient.contains("cn")) {
                String[] ingredientPiece = ingredient.split(" ");
                int canIndex = ingredient.contains("can") ? Arrays.asList(ingredientPiece).indexOf("can") : Arrays.asList(ingredientPiece).indexOf("cn");
                for (int i = canIndex+1; i < ingredientPiece.length; i++) {
                    name.append(ingredientPiece[i]);
                    name.append(" ");
                }
                if(Character.isDigit(ingredientPiece[0].charAt(0))){
                    if(Character.isDigit(ingredientPiece[1].charAt(0))) {
                        amount.append(StringToDoubleConverter.convertStringWithSlashToDouble(ingredientPiece[0]) * StringToDoubleConverter.convertStringWithSlashToDouble(ingredientPiece[1]));
                        unit = ingredientPiece[2];
                    } else {
                        amount.append(ingredientPiece[0]);
                        unit = ingredientPiece[1];
                    }
                }
            } else
            if(ingredient.contains("and")) {
                for (String ingredientPiece : ingredient.split(" and ")){
                    ImportedIngredientDTO importedIngredient = new ImportedIngredientDTO();
                    importedIngredient.setName(ingredientPiece);
                    importedIngredient.setAmount("1");
                    importedIngredient.setUnit("PINCH");
                    importedIngredients.add(importedIngredient);
                }
            } else {
                String[] ingredientPiece = ingredient.split(" ");
                for (int i = 0; i < ingredientPiece.length; i++) {
                    if(Character.isUpperCase(ingredientPiece[i].charAt(0))) {
                        for (int j = i; j < ingredientPiece.length; j++) {
                            name.append(ingredientPiece[j]);
                            name.append(" ");
                        }
                        break;
                    } else
                    if(Character.isDigit(ingredientPiece[i].charAt(0))) {
                        if(Character.isDigit(ingredientPiece[i+1].charAt(0))){
                            amount.append(ingredientPiece[i]);
                            amount.append(ingredientPiece[i + 1]);
                            i++;
                        } else {
                            amount = new StringBuilder(ingredientPiece[i]);
                        }
                    } else {
                        unit = ingredientPiece[i];
                    }
                }
                if(Objects.equals(unit, "")) unit = "PIECE";
                if(Objects.equals(String.valueOf(amount), "")) amount = new StringBuilder("1");
                ImportedIngredientDTO importedIngredient = new ImportedIngredientDTO();
                importedIngredient.setName(String.valueOf(name));
                importedIngredient.setUnit(unit);
                importedIngredient.setAmount(String.valueOf(amount));
                importedIngredients.add(importedIngredient);
            }
        }
        return importedIngredients;
    }
}
