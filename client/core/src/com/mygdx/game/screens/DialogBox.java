package com.mygdx.game.screens;

import com.mygdx.game.requests.JsonHandler;
import com.mygdx.game.requests.PlayerAccount;

import org.json.JSONException;

import java.io.IOException;

import de.tomgrill.gdxdialogs.core.GDXDialogs;
import de.tomgrill.gdxdialogs.core.GDXDialogsSystem;
import de.tomgrill.gdxdialogs.core.dialogs.GDXButtonDialog;
import de.tomgrill.gdxdialogs.core.listener.ButtonClickListener;

public class DialogBox {
    private static GDXDialogs dialogs = null;

    static {
        if (dialogs == null)
            dialogs = GDXDialogsSystem.install();
    }

    public static void     showInfoDialog(String title, String message) {
        final GDXButtonDialog bDialog = dialogs.newDialog(GDXButtonDialog.class);
        bDialog.setTitle(title);
        bDialog.setMessage(message);
        bDialog.addButton("OK");
        bDialog.setClickListener(new ButtonClickListener() {

            @Override
            public void click(int button) {
            }
        });
        bDialog.show();
    }
}
