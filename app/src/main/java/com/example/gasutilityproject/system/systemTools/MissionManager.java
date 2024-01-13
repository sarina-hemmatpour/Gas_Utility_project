package com.example.gasutilityproject.system.systemTools;

import com.example.gasutilityproject.Data.Model.Mission;

public class MissionManager {
    public static boolean validateLocation(Mission mission){
        double missionLong=Double.parseDouble(mission.getLocation().split(":")[0].trim());
        double missionLat=Double.parseDouble(mission.getLocation().split(":")[1].trim());
        double techLong=Double.parseDouble(mission.getTechnicianLocation().split(":")[0].trim());
        double techLat=Double.parseDouble(mission.getTechnicianLocation().split(":")[1].trim());
        if (Math.abs(techLat-missionLat)>0.003787383d)
            return false;
        return !(Math.abs(techLong - missionLong) > 0.003787383d);
    }
}
