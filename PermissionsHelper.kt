@TargetApi(Build.VERSION_CODES.M)
object PermissionsHelper {

    val permissionsSet: Set<Permissions>
        get() {
            val permissionsSet = HashSet<Permissions>()
            permissionsSet.add(Permissions.REQUEST_WRITE_STORAGE)
            permissionsSet.add(Permissions.REQUEST_CAMERA)
            return permissionsSet
        }

    fun checkPermission(context: Activity, permission: Permissions, action: PermissionAction) {
        val hasGranted = hasGranted(context.checkSelfPermission(permission.permission))
        if (hasGranted) {
            action.onPermissionGranted(permission)
        } else {
            action.onPermissionDenied(permission)
        }
    }

    fun checkPermission(fragment: Fragment, context: Context, permission: Permissions, action: PermissionAction) {
        val hasGranted = hasGranted(context.checkSelfPermission(permission.permission))
        if (hasGranted) {
            action.onPermissionGranted(permission)
        } else {
            action.onPermissionDenied(permission)
        }
    }

    fun checkPermissions(context: Activity, permissions: Set<Permissions>, action: PermissionActions) {
        var hasGranted = false
        val permissionsList = ArrayList<String>()

        if (permissions.isEmpty()) return

        for (permission in permissions) {
            permissionsList.add(permission.permission)
        }

        for (permission in permissions) {
            hasGranted = hasGranted(context.checkSelfPermission(permission.permission))
            if (!hasGranted) break
        }

        if (hasGranted) {
            action.onPermissionGranted(permissions)
        } else {
            action.onPermissionDenied(permissions)
        }
    }

    fun checkPermissions(fragment: Fragment, context: Context, permissions: Set<Permissions>, action: PermissionActions) {
        var hasGranted = false
        val permissionsList = ArrayList<String>()

        if (permissions.isEmpty()) return

        for (permission in permissions) {
            permissionsList.add(permission.permission)
        }

        for (permission in permissions) {
            hasGranted = hasGranted(context.checkSelfPermission(permission.permission))
            if (!hasGranted) break
        }

        if (hasGranted) {
            action.onPermissionGranted(permissions)
        } else {
            action.onPermissionDenied(permissions)
        }
    }

    fun grandPermissions(fragment: Fragment, permissions: Set<Permissions>) {
        val permissionsList = ArrayList<String>()
        for (permission in permissions) {
            permissionsList.add(permission.permission)
        }
        val permissionsArray = permissionsList.toTypedArray()
        fragment.requestPermissions(permissionsArray, Const.REQUEST_PERMISSIONS)
    }

    fun grandPermission(fragment: Fragment, permission: Permissions) {
        fragment.requestPermissions(arrayOf<String>(permission.permission), permission.id)
    }

    fun grandPermissions(context: Activity, permissions: Set<Permissions>) {
        val permissionsList = ArrayList<String>()
        for (permission in permissions) {
            permissionsList.add(permission.permission)
        }
        val permissionsArray = permissionsList.toTypedArray()
        context.requestPermissions(permissionsArray, Const.REQUEST_PERMISSIONS)
    }

    fun grandPermission(context: Activity, permission: Permissions) {
        context.requestPermissions(arrayOf<String>(permission.permission), permission.id)
    }

    fun showPermissionReasonDialog(context: Activity, permissions: Set<Permissions>): Boolean {
        var hasGranted = false
        var permissionNotGranted: Permissions = Permissions.PERMISSION_MISSING
        for (permission in permissions) {
            permissionNotGranted = permission
            hasGranted = hasGranted(context.checkSelfPermission(permission.permission))
            if (!hasGranted) break
        }

        if (hasGranted) return true

        return context.shouldShowRequestPermissionRationale(permissionNotGranted.permission)
    }

    fun hasGranted(grantResult: Int): Boolean {
        return grantResult == PackageManager.PERMISSION_GRANTED
    }

    fun hasGranted(grantResults: IntArray): Boolean {
        for (result in grantResults) {
            if (!hasGranted(result)) {
                return false
            }
        }
        return true
    }

    interface PermissionAction {
        fun onPermissionGranted(permission: Permissions)
        fun onPermissionDenied(permission: Permissions)
    }

    interface PermissionActions {
        fun onPermissionGranted(permissions: Set<Permissions>)
        fun onPermissionDenied(permissions: Set<Permissions>)
    }
}
