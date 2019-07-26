package com.gdg.pune.devfest19.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.gdg.pune.devfest19.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.DividerDrawerItem
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.Nameable
import com.shreyaspatil.MaterialDialog.BottomSheetMaterialDialog
import com.shreyaspatil.MaterialDialog.MaterialDialog
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private var result: Drawer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val itemHome =
            PrimaryDrawerItem().withName(R.string.menu_home).withIcon(R.drawable.ic_home).withTintSelectedIcon(true)
                .withIconColor(resources.getColor(R.color.dark_gray))
                .withSelectedIconColor(resources.getColor(R.color.md_purple_400))
        val itemSchedule = PrimaryDrawerItem().withName(R.string.menu_schedule).withIcon(R.drawable.ic_menu_schedule)
            .withTintSelectedIcon(true).withIconColor(resources.getColor(R.color.dark_gray))
            .withSelectedIconColor(resources.getColor(R.color.md_purple_400))
        val itemSpeakers = PrimaryDrawerItem().withName(R.string.menu_speakers).withIcon(R.drawable.ic_person)
            .withTintSelectedIcon(true).withIconColor(resources.getColor(R.color.dark_gray))
            .withSelectedIconColor(resources.getColor(R.color.md_purple_400))
        val itemAnnouncements =
            PrimaryDrawerItem().withName(R.string.menu_announcements).withIcon(R.drawable.ic_notifications)
                .withTintSelectedIcon(true).withIconColor(resources.getColor(R.color.dark_gray))
                .withSelectedIconColor(resources.getColor(R.color.md_purple_400))
        val itemDiscuss = PrimaryDrawerItem().withName(R.string.menu_discuss).withIcon(R.drawable.ic_people)
            .withTintSelectedIcon(true).withIconColor(resources.getColor(R.color.dark_gray))
            .withSelectedIconColor(resources.getColor(R.color.md_purple_400))
        val itemFindWay = PrimaryDrawerItem().withName(R.string.menu_find_your_way).withIcon(R.drawable.ic_location)
            .withTintSelectedIcon(true).withIconColor(resources.getColor(R.color.dark_gray))
            .withSelectedIconColor(resources.getColor(R.color.md_purple_400))
        val itemOrganiser = PrimaryDrawerItem().withName(R.string.menu_organiser).withIcon(R.drawable.ic_organizers)
            .withTintSelectedIcon(true).withIconColor(resources.getColor(R.color.dark_gray))
            .withSelectedIconColor(resources.getColor(R.color.md_purple_400))
        val itemDeveloper = SecondaryDrawerItem().withName(R.string.menu_developers).withIcon(R.drawable.ic_menu_manage)
            .withTintSelectedIcon(true).withIconColor(resources.getColor(R.color.dark_gray))
            .withSelectedIconColor(resources.getColor(R.color.md_purple_400))
        val itemLogout =
            SecondaryDrawerItem().withName(R.string.menu_logout).withIcon(R.drawable.ic_exit).withTintSelectedIcon(true)
                .withIconColor(resources.getColor(R.color.dark_gray))
                .withSelectedIconColor(resources.getColor(R.color.md_purple_400))

        result = DrawerBuilder()
            .withActivity(this)
            .withToolbar(toolbar)
            .withTranslucentStatusBar(false)
            .withHeader(R.layout.nav_header)
            .withSavedInstance(savedInstanceState)
            .addDrawerItems(
                itemHome,
                itemSchedule,
                itemSpeakers,
                itemAnnouncements,
                itemDiscuss,
                itemFindWay,
                itemOrganiser,
                DividerDrawerItem(),
                itemDeveloper,
                itemLogout
            )
            //.inflateMenu(R.menu.activity_main2_drawer)
            .withOnDrawerItemClickListener { view, position, drawerItem ->
                if (drawerItem is Nameable<*>) {
                    Toast.makeText(
                        this@MainActivity,
                        (drawerItem as Nameable<*>).name!!.getText(this@MainActivity),
                        Toast.LENGTH_SHORT
                    ).show()
                }

                false
            }.build()

    }

    override fun onBackPressed() {
        if (result?.isDrawerOpen == true) {
            result?.closeDrawer()
        } else {
            MaterialDialog.Builder(this@MainActivity)
                .setTitle("Exit?")
                .setMessage("Are you sure want to exit?")
                .setPositiveButton("Yes") { _, _ ->
                    finish()
                }
                .setNegativeButton("No") { dialogInterface, _ ->
                    dialogInterface.dismiss()
                }
                .build()
                .show()
        }
    }

    override fun onStart() {
        super.onStart()

        mAuth = FirebaseAuth.getInstance()
        val firebaseUser = mAuth.currentUser

        if (firebaseUser == null) {
            onAuthNotFound()

        } else if (firebaseUser.displayName == null || firebaseUser.email == null || firebaseUser.uid == null) {
            onAuthNotFound()
        } else {
            mAuth.addAuthStateListener {
                if (it.currentUser == null) {
                    onAuthNotFound()
                }
            }
        }
    }

    private fun onAuthNotFound() {
        val intent = Intent(this, SignInActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun promptSignOut() {

        BottomSheetMaterialDialog.Builder(this)
            .setTitle("Sign out?")
            .setMessage("Are you sure want to Sign out?")
            .setPositiveButton(
                "Sign Out"
            ) { dialogInterface, which -> signOut() }
            .setNegativeButton(
                "No"
            ) { dialogInterface, which ->
                if (dialogInterface != null) {
                    dialogInterface.dismiss()
                }
            }
            .build()
            .show()
    }

    private fun signOut() {
        mAuth.signOut()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        val googleSignInClient = GoogleSignIn.getClient(this, gso)

        googleSignInClient.signOut().addOnCompleteListener(this) {
            onAuthNotFound()
        }
    }
}
