enum class Permissions(val id: Int, val permission: String) {
    REQUEST_CAMERA(0, Manifest.permission.CAMERA),
    REQUEST_READ_STORAGE(1, Manifest.permission.READ_EXTERNAL_STORAGE),
    REQUEST_WRITE_STORAGE(2, Manifest.permission.WRITE_EXTERNAL_STORAGE),
    PERMISSION_MISSING(-1, "");

    companion object {
        fun findById(id: Int): Permissions {
            for (item in Permissions.values()) {
                if (item.id == id)
                    return item
            }
            return Permissions.PERMISSION_MISSING
        }

        fun findByPermission(permission: String): Permissions {
            for (item in Permissions.values()) {
                if (item.permission == permission)
                    return item
            }
            return Permissions.PERMISSION_MISSING
        }
    }

}