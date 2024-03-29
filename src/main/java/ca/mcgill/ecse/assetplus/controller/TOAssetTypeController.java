package ca.mcgill.ecse.assetplus.controller;

import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;
import ca.mcgill.ecse.assetplus.model.AssetPlus;
import ca.mcgill.ecse.assetplus.model.AssetType;

import java.util.ArrayList;

public class TOAssetTypeController {

    /**
    * gets a list of all the assets types from the assetplus object and returns them as transfer objects
   * @return assetTypes (list of TO asset types)
   * @author David Marji.
   */
    public static ArrayList<TOAssetType> getAssetTypes() {
        ArrayList<TOAssetType> assetTypes = new ArrayList<TOAssetType>();
        AssetPlus assetPlus = AssetPlusApplication.getAssetPlus();

        for (AssetType assetType : assetPlus.getAssetTypes()) {
            String name = assetType.getName();
            int expectedLifeSpan = assetType.getExpectedLifeSpan();
            assetTypes.add(new TOAssetType(expectedLifeSpan, name));
        }

        return assetTypes;
    }

}