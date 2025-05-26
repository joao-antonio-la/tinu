package com.example.tinu;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

public class MainFunctionActivity extends AppCompatActivity {
    private List<String> allOpts = List.of("Área", "Comprimento", "Temperatura", "Volume", "Massa", "Dados", "Velocidade", "Tempo");
    private UnitOptions.grandeza grandezaAtual = UnitOptions.grandeza.AREA;
    private List<String> options = UnitOptions.getUnidades(grandezaAtual);
    private String selectedGrandeza = "Área";
    private Enum<?> selectedTopUnity = UnitOptions.area.M;
    private Enum<?> selectedBottomUnity = UnitOptions.area.CM;
    private boolean isTopMenuActive = true;
    private List<TextView> textDisplays;
    private int activeDisplay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_function);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        RecyclerView recyclerView = findViewById(R.id.carouselRecyclerView);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        );

        grandezaAtual = UnitOptions.parseGrandeza(selectedGrandeza);
        options = UnitOptions.getUnidades(grandezaAtual);

        // Seleciona as duas primeiras unidades como padrão
        if (options.size() >= 2) {
            String topUnitName = "Metros Quadrados";
            String bottomUnitName = "Centímetros Quadrados";

            selectedTopUnity = UnitOptions.getEnumFromNome(topUnitName);
            selectedBottomUnity = UnitOptions.getEnumFromNome(bottomUnitName);

            String topAbbr = UnitOptions.getAbbreviationFromNome(topUnitName);
            String bottomAbbr = UnitOptions.getAbbreviationFromNome(bottomUnitName);

            ((TextView) findViewById(R.id.top_dropdown_menu_unit_name)).setText(topUnitName + " (" + topAbbr + ")");
            ((TextView) findViewById(R.id.top_unit)).setText(topAbbr);

            ((TextView) findViewById(R.id.bottom_dropdown_menu_unit_name)).setText(bottomUnitName + " (" + bottomAbbr + ")");
            ((TextView) findViewById(R.id.bottom_unit)).setText(bottomAbbr);
        }

        CarouselAdapter adapter = new CarouselAdapter(allOpts, itemName -> {
            selectedGrandeza = itemName;
            grandezaAtual = UnitOptions.parseGrandeza(selectedGrandeza);
            options = UnitOptions.getUnidades(grandezaAtual);

            if (options.size() >= 2) {
                String topUnitName = options.get(0);
                String bottomUnitName = options.get(1);

                selectedTopUnity = UnitOptions.getEnumFromNome(topUnitName);
                selectedBottomUnity = UnitOptions.getEnumFromNome(bottomUnitName);

                String topAbbr = UnitOptions.getAbbreviationFromNome(topUnitName);
                String bottomAbbr = UnitOptions.getAbbreviationFromNome(bottomUnitName);

                ((TextView) findViewById(R.id.top_dropdown_menu_unit_name)).setText(topUnitName + " (" + topAbbr + ")");
                ((TextView) findViewById(R.id.top_unit)).setText(topAbbr);

                ((TextView) findViewById(R.id.bottom_dropdown_menu_unit_name)).setText(bottomUnitName + " (" + bottomAbbr + ")");
                ((TextView) findViewById(R.id.bottom_unit)).setText(bottomAbbr);

                updateConversion();
            }
        });

        recyclerView.setAdapter(adapter);


        LinearLayout top_unit_menu = findViewById(R.id.top_menu_access);
        LinearLayout bottom_unit_menu = findViewById(R.id.bottom_menu_access);

            top_unit_menu.setOnClickListener(v -> {
            isTopMenuActive = true;
            grandezaAtual = UnitOptions.parseGrandeza(selectedGrandeza);
            options = UnitOptions.getUnidades(grandezaAtual);
            showOptionsCard(v, options);
        });

        bottom_unit_menu.setOnClickListener(v -> {
            isTopMenuActive = false;
            grandezaAtual = UnitOptions.parseGrandeza(selectedGrandeza);
            options = UnitOptions.getUnidades(grandezaAtual);
            showOptionsCard(v, options);
        });

        textDisplays = List.of(
                findViewById(R.id.top_value),
                findViewById(R.id.bottom_value)
        );
        activeDisplay = 0;

        findViewById(R.id.top_area).setOnClickListener(v -> { activeDisplay = 0; });
        findViewById(R.id.bottom_area).setOnClickListener(v -> { activeDisplay = 1; });

        Button btn0 = findViewById(R.id.button0);
        Button btn1 = findViewById(R.id.button1);
        Button btn2 = findViewById(R.id.button2);
        Button btn3 = findViewById(R.id.button3);
        Button btn4 = findViewById(R.id.button4);
        Button btn5 = findViewById(R.id.button5);
        Button btn6 = findViewById(R.id.button6);
        Button btn7 = findViewById(R.id.button7);
        Button btn8 = findViewById(R.id.button8);
        Button btn9 = findViewById(R.id.button9);
        List<Button> digits = List.of(btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9);

        for (Button b : digits) {
            b.setOnClickListener(v -> {
                TextView current = textDisplays.get(activeDisplay);
                if (current.getText().toString().equals("0"))
                    current.setText(b.getText());
                else
                    current.append(b.getText());
                updateConversion();
            });
        }

        Button btnSignal = findViewById(R.id.buttonsignal);
        btnSignal.setOnClickListener(v -> {
            TextView current = textDisplays.get(activeDisplay);
            String text = current.getText().toString();
            if (text.isEmpty() || text.equals("0")) {
                current.setText("-0");
            } else if (text.charAt(0) == '-') {
                current.setText(text.substring(1));
            } else {
                current.setText("-" + text);
            }
            updateConversion();
        });

        Button btnComma = findViewById(R.id.buttoncomma);
        btnComma.setOnClickListener(v -> {
            TextView current = textDisplays.get(activeDisplay);
            String text = current.getText().toString();
            if (text.isEmpty()) {
                current.setText("0,");
            } else if (!text.contains(",")) {
                current.append(",");
            }
            updateConversion();
        });

        Button btnBackspace = findViewById(R.id.buttonbkspc);
        btnBackspace.setOnClickListener(v -> {
            TextView current = textDisplays.get(activeDisplay);
            String text = current.getText().toString();
            if (!text.isEmpty()) {
                text = text.substring(0, text.length() - 1);
                current.setText(text.isEmpty() ? "0" : text);
            }
            updateConversion();
        });

        Button btnClear = findViewById(R.id.buttonclear);
        btnClear.setOnClickListener(v -> {
            textDisplays.get(activeDisplay).setText("");
            updateConversion();
        });

        Button btnGoUp = findViewById(R.id.buttongoup);
        btnGoUp.setOnClickListener(v -> {
            activeDisplay = 0;
        });

        Button btnGoDown = findViewById(R.id.buttongodown);
        btnGoDown.setOnClickListener(v -> {
            activeDisplay = 1;
        });

        findViewById(R.id.menu_burger).setOnClickListener(this::showMenuCard);
    }

    private void updateConversion() {
        String input = textDisplays.get(activeDisplay).getText().toString().replace(',', '.');
        String result;
        if (activeDisplay == 0)
            result = OperationHandler.executeConversion(grandezaAtual, selectedTopUnity, selectedBottomUnity, input);
        else
            result = OperationHandler.executeConversion(grandezaAtual, selectedBottomUnity, selectedTopUnity, input);
        textDisplays.get(activeDisplay ^ 1).setText(result);
    }

    public void showOptionsCard(View anchorView, List<String> options) {
        // Parent layout (vertical)
        LinearLayout cardLayout = new LinearLayout(this);
        cardLayout.setOrientation(LinearLayout.VERTICAL);
        cardLayout.setPadding(30, 30, 30, 30);
        cardLayout.setBackgroundColor(Color.WHITE);

        // Rounded corners
        GradientDrawable background = new GradientDrawable();
        background.setCornerRadius(30f);
        background.setColor(Color.WHITE);
        background.setStroke(2, Color.LTGRAY);
        cardLayout.setBackground(background);

        // Create the PopupWindow first so we can reference it in listeners
        final PopupWindow popupWindow = new PopupWindow(cardLayout,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true);
        popupWindow.setElevation(10);

        // Add options
        for (int i = 0; i < options.size(); i++) {
            String optionText = options.get(i);

            TextView option = new TextView(this);
            option.setText(optionText);
            option.setTextSize(18);
            option.setPadding(20, 20, 20, 20);
            option.setTextColor(Color.DKGRAY);
            option.setClickable(true);

            option.setOnClickListener(v -> {
                Enum<?> selectedEnum = UnitOptions.getEnumFromNome(optionText);
                String textAbrv = UnitOptions.getAbbreviationFromNome(optionText);
                String text = optionText + " (" + textAbrv + ")";

                if (isTopMenuActive) {
                    if (selectedEnum.equals(selectedBottomUnity)) {
                        // Swap the values
                        Enum<?> temp = selectedTopUnity;
                        selectedTopUnity = selectedBottomUnity;
                        selectedBottomUnity = temp;

                        // Update UI
                        updateUnitTextViews();
                    } else {
                        selectedTopUnity = selectedEnum;
                        ((TextView) findViewById(R.id.top_dropdown_menu_unit_name)).setText(text);
                        ((TextView) findViewById(R.id.top_unit)).setText(textAbrv);
                    }
                } else {
                    if (selectedEnum.equals(selectedTopUnity)) {
                        // Swap the values
                        Enum<?> temp = selectedBottomUnity;
                        selectedBottomUnity = selectedTopUnity;
                        selectedTopUnity = temp;

                        // Update UI
                        updateUnitTextViews();
                    } else {
                        selectedBottomUnity = selectedEnum;
                        ((TextView) findViewById(R.id.bottom_dropdown_menu_unit_name)).setText(text);
                        ((TextView) findViewById(R.id.bottom_unit)).setText(textAbrv);
                    }
                }

                updateConversion();
                popupWindow.dismiss();
            });


            cardLayout.addView(option);

            // Separator
            if (i < options.size() - 1) {
                View divider = new View(this);
                LinearLayout.LayoutParams dividerParams = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, 4);
                dividerParams.setMargins(0, 5, 0, 5);
                divider.setLayoutParams(dividerParams);
                divider.setBackgroundColor(Color.parseColor("#45989D"));
                cardLayout.addView(divider);
            }
        }

        // Show the popup in the center
        popupWindow.showAtLocation(anchorView, Gravity.CENTER, 0, 0);
    }

    private void updateUnitTextViews() {
        String topUnitName = UnitOptions.getNomeFromEnum(selectedTopUnity);
        String bottomUnitName = UnitOptions.getNomeFromEnum(selectedBottomUnity);

        String topAbbr = UnitOptions.getAbbreviationFromNome(topUnitName);
        String bottomAbbr = UnitOptions.getAbbreviationFromNome(bottomUnitName);

        ((TextView) findViewById(R.id.top_dropdown_menu_unit_name)).setText(topUnitName + " (" + topAbbr + ")");
        ((TextView) findViewById(R.id.top_unit)).setText(topAbbr);

        ((TextView) findViewById(R.id.bottom_dropdown_menu_unit_name)).setText(bottomUnitName + " (" + bottomAbbr + ")");
        ((TextView) findViewById(R.id.bottom_unit)).setText(bottomAbbr);
    }


    public void showMenuCard(View anchorView) {
        // Parent layout (vertical)
        LinearLayout cardLayout = new LinearLayout(this);
        cardLayout.setOrientation(LinearLayout.VERTICAL);
        cardLayout.setPadding(30, 30, 30, 30);
        cardLayout.setBackgroundColor(Color.WHITE);

        // Rounded corners
        GradientDrawable background = new GradientDrawable();
        background.setCornerRadius(30f);
        background.setColor(Color.WHITE);
        background.setStroke(2, Color.LTGRAY);
        cardLayout.setBackground(background);

        // Create the PopupWindow first so we can reference it in listeners
        final PopupWindow popupWindow = new PopupWindow(cardLayout,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true);
        popupWindow.setElevation(10);

        class Helper {
            void insertSeparator() {
                View divider = new View(cardLayout.getContext());
                LinearLayout.LayoutParams dividerParams = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, 4);
                dividerParams.setMargins(0, 5, 0, 5);
                divider.setLayoutParams(dividerParams);
                divider.setBackgroundColor(Color.parseColor("#45989D"));
                cardLayout.addView(divider);
            }
        }

        String optionText;

        optionText = "Conversão Múltipla";

        TextView option1 = new TextView(this);
        option1.setText(optionText);
        option1.setTextSize(18);
        option1.setPadding(20, 20, 20, 20);
        option1.setTextColor(Color.DKGRAY);
        option1.setClickable(true);

        option1.setOnClickListener(v -> {
            Toast.makeText(this, "Ainda não implementado!", Toast.LENGTH_SHORT).show();
            popupWindow.dismiss();
        });

        cardLayout.addView(option1);
        new Helper().insertSeparator();

        optionText = "Fechar o App";

        TextView option2 = new TextView(this);
        option2.setText(optionText);
        option2.setTextSize(18);
        option2.setPadding(20, 20, 20, 20);
        option2.setTextColor(Color.DKGRAY);
        option2.setClickable(true);

        option2.setOnClickListener(v -> {
            finishAffinity();
            popupWindow.dismiss();
        });


        cardLayout.addView(option2);
        new Helper().insertSeparator();

        optionText = "Sair";

        TextView option3 = new TextView(this);
        option3.setText(optionText);
        option3.setTextSize(18);
        option3.setPadding(20, 20, 20, 20);
        option3.setTextColor(Color.DKGRAY);
        option3.setClickable(true);

        option3.setOnClickListener(v -> {
            Intent intent = new Intent(MainFunctionActivity.this, AcessActivity.class);
            startActivity(intent);
            popupWindow.dismiss();
        });

        cardLayout.addView(option3);

        popupWindow.showAtLocation(anchorView, Gravity.CENTER, 0, 0);
    }
}