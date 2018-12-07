# RunTimePermissionsHelper


Checklist:
- [x] Single permission check
- [x] Miltiple permissions check
- [x] Handle after reject state permission check
- [ ] Documentation
- [ ] Full list of permissions in enum
- [ ] Refactor code


There are interfaces for interaction with **PermissionsHelper**:
- PermissionAction
- PermissionActions

```kotlin
interface PermissionAction {
    fun onPermissionGranted(permission: Permissions)
    fun onPermissionDenied(permission: Permissions)
}

interface PermissionActions {
    fun onPermissionGranted(permissions: Set<Permissions>)
    fun onPermissionDenied(permissions: Set<Permissions>)
}
```


Example of usage in Activity: 
```kotlin
class MainActivity : AppCompatActivity(), PermissionsHelper.PermissionActions {
     override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(R.layout.activity_main)
      provideViewModel()

      // You can use it in an Activity or in a Fragment.
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
          // You can provide your own set or just ask for single permission, check checkPermission() method.
          PermissionsHelper.checkPermissions(this, PermissionsHelper.permissionsSet, this)
      }
    }
    
    override fun onPermissionGranted(permissions: Set<Permissions>) {
        /* do something or just ignore. */
    }

    override fun onPermissionDenied(permissions: Set<Permissions>) {
      if (PermissionsHelper.showPermissionReasonDialog(this, permissions)) {
          val dialog = SomeDialog.getInstance()
          dialog.setOnDismissListener(DialogInterface.OnDismissListener {
              // Ask for permissions again if a user reject it before.
              PermissionsHelper.grandPermissions(this, permissions)
              dialog.dismiss()
          })
          dialog.show(supportFragmentManager, TAG_DIALOG)
      } else {
          // Ask for permissions first time.
          PermissionsHelper.grandPermissions(this, permissions)
      }
    }
}
```
