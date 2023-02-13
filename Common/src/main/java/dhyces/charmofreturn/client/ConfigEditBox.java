package dhyces.charmofreturn.client;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.tokenizer.UnknownFunctionOrVariableException;

public class ConfigEditBox extends EditBox {

    protected OnFocusLost callback;
    protected String defaultValue;

    public ConfigEditBox(Font font, int x, int y, int width, int height, Component message, OnFocusLost callback, String defaultValue) {
        super(font, x, y, width, height, message);
        this.callback = callback;
        this.defaultValue = defaultValue;
        setValue(this.defaultValue);
    }

    public void setValue(int number) {
        this.setValue(String.valueOf(number));
    }

    public void setValue(double number) {
        this.setValue(String.valueOf(number));
    }

    public int parseIntValue() {
        try {
            return Integer.parseInt(getValue());
        } catch (NumberFormatException e) {
            setValue(defaultValue);
        }
        return Integer.parseInt(getValue());
    }

    public boolean parseBooleanValue() {
        boolean ret = false;
        if (!"true".equalsIgnoreCase(getValue()) && !"false".equalsIgnoreCase(getValue())) {
            setValue(defaultValue);
        }
        return Boolean.parseBoolean(getValue());
    }

    public Expression parseExpression() {
        Expression exp;
        try {
            exp = new ExpressionBuilder(getValue()).variable("x").build();
        } catch (UnknownFunctionOrVariableException e) {
            setValue(defaultValue);
            exp = new ExpressionBuilder(getValue()).variable("x").build();
        }
        return exp;
    }

    @Override
    public void setFocus(boolean $$0) {
        if (this.isFocused() != $$0) {
            onFocusedChanged($$0);
        }
        super.setFocus($$0);
    }

    @Override
    protected void onFocusedChanged(boolean isFocused) {
        super.onFocusedChanged(isFocused);
        if (!isFocused) {
            callback.onFocusLost(this);
        }
    }

    public interface OnFocusLost {
        void onFocusLost(ConfigEditBox box);
    }
}
