package com.github.abdularis.trackmylocation.permission;

/**
 * Convenient methods to check permissions and if they have already been shown
 */
public interface PermissionChecker {
    boolean hasPermission(PermissionType permissionType);

    boolean hasPermissionBeenShown(PermissionType permissionType);

    boolean hasPermissionChanged(PermissionType permissionType);

    void updatePermissionState(PermissionType permissionType);
}
