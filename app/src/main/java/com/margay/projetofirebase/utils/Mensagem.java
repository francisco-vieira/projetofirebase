package com.margay.projetofirebase.utils;

import android.graphics.Color;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.margay.projetofirebase.enums.Msg;

import java.util.HashMap;
import java.util.Map;

public class Mensagem {

    private final Msg value;

    private Mensagem(Msg value) {
        this.value = value;
    }

    public static String getMessage(Msg value) {
        return new Mensagem(value).msg();
    }

    private String msg() {
        return this.listMessage().get(value);
    }

    private Map<Msg, String> listMessage(){
        return new HashMap<Msg, String>(){{
            put(Msg.TODOS_CAMPOS, "Preencha todos os campos!");
            put(Msg.CADASTRO_SUCESSO, "Salvo com sucesso!");
            put(Msg.CADASTRO_ERRO, "Erro ao salvar email");
            put(Msg.LOGIN_ERRO, "Erro ao logar no sistema");
        }};
    }
    public static void messagem(View v, String returnMessage) {
        Snackbar.make(v, returnMessage, Snackbar.LENGTH_SHORT)
                .setBackgroundTint(Color.WHITE)
                .setTextColor(Color.BLACK)
                .show();
    }

}
