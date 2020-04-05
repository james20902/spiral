package frc.lib;

import edu.wpi.first.networktables.NetworkTableInstance;

public class NetworkTableManager {

  private static NetworkTableManager instance;

  public static NetworkTableManager getInstance() {
    if(instance == null){
      instance = new NetworkTableManager();
      NetworkTableInstance inst = NetworkTableInstance.getDefault();
      inst.startServer();
      inst.setNetworkIdentity("Robot");
    }
    return instance;
  }
}
