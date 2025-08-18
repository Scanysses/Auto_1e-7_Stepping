package dev.scanysses.auto_1e7_stepping;

public enum PositioningMode {
    PACKET, // Packet positioning (ideally calculated micro move)
    SCRIPT // Sequence of actions that player can do manually, but a bit faster

}
