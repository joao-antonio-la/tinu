package com.example.tinu;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ExtensionActivity extends AppCompatActivity {

    private final List<String> allOpts = List.of("Área", "Comprimento", "Temperatura", "Volume", "Massa", "Dados", "Velocidade", "Tempo");
    private UnitOptions.grandeza grandezaAtual = UnitOptions.grandeza.AREA;
    private List<String> options = UnitOptions.getUnidades(grandezaAtual);
    private final String selectedGrandeza = "Área";
    private LinearLayout container;
    private View activeDisplay;
    private int activeDisplayIdx;
    private final List<View> allDisplays = new ArrayList<View>();
    private final List<Integer> displaysIds = new ArrayList<Integer>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_extension);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        findViewById(R.id.menu_burger).setOnClickListener(this::showMenuCard);

        this.container = findViewById(R.id.units_container);

        RecyclerView recyclerView = findViewById(R.id.carouselRecyclerView);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        );

        CarouselAdapter adapter = new CarouselAdapter(allOpts, itemName -> {
            grandezaAtual = UnitOptions.parseGrandeza(itemName);
            loadUnits(grandezaAtual);
        });

        recyclerView.setAdapter(adapter);

        loadUnits(grandezaAtual);

        if (!allDisplays.isEmpty()) {
            activeDisplay = allDisplays.get(0);
            activeDisplayIdx = 0;
        }

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
                TextView tv = activeDisplay.findViewById(R.id.value);
                if (tv.getText().toString().equals("0"))
                    tv.setText(b.getText());
                else
                    tv.append(b.getText());
                updateDisplays();
            });
        }

        Button btnSignal = findViewById(R.id.buttonsignal);
        btnSignal.setOnClickListener(v -> {
            TextView tv = activeDisplay.findViewById(R.id.value);
            String text = tv.getText().toString();
            if (text.isEmpty() || text.equals("0")) {
                tv.setText("-0");
            } else if (text.charAt(0) == '-') {
                tv.setText(text.substring(1));
            } else {
                tv.setText("-" + text);
            }
            updateDisplays();
        });

        Button btnComma = findViewById(R.id.buttoncomma);
        btnComma.setOnClickListener(v -> {
            TextView tv = activeDisplay.findViewById(R.id.value);
            String text = tv.getText().toString();
            if (text.isEmpty()) {
                tv.setText("0,");
            } else if (!text.contains(",")) {
                tv.append(",");
            }
        });

        Button btnBackspace = findViewById(R.id.buttonbkspc);
        btnBackspace.setOnClickListener(v -> {
            TextView tv = activeDisplay.findViewById(R.id.value);
            String text = tv.getText().toString();
            if (!text.isEmpty()) {
                text = text.substring(0, text.length() - 1);
                tv.setText(text.isEmpty() ? "0" : text);
            }
            updateDisplays();
        });

        Button btnClear = findViewById(R.id.buttonclear);
        btnClear.setOnClickListener(v -> {
            TextView tv = activeDisplay.findViewById(R.id.value);
            tv.setText("0");
            updateDisplays();
        });

        Button btnGoUp = findViewById(R.id.buttongoup);
        btnGoUp.setOnClickListener(v -> {
            if (activeDisplayIdx > 0) {
                activeDisplayIdx--;
                activeDisplay = allDisplays.get(activeDisplayIdx);
            }
        });

        Button btnGoDown = findViewById(R.id.buttongodown);
        btnGoDown.setOnClickListener(v -> {
            if (activeDisplayIdx < allDisplays.size() - 1) {
                activeDisplayIdx++;
                activeDisplay = allDisplays.get(activeDisplayIdx);
            }
        });

    }

    private void addUnitCell(LinearLayout container, String nameText, String valueText, String unitText) {
        View cell = LayoutInflater.from(this).inflate(R.layout.reusable_unit_cell, container, false);

        int newId = View.generateViewId();
        cell.setId(newId);
        ((TextView) cell.findViewById(R.id.unit_name)).setText(nameText);
        ((TextView) cell.findViewById(R.id.value)).setText(valueText);
        ((TextView) cell.findViewById(R.id.unit)).setText(unitText);

        displaysIds.add(newId);
        allDisplays.add(cell);

        container.addView(cell);
    }

    private void addLine(LinearLayout container) {
        View cell = LayoutInflater.from(this).inflate(R.layout.line, container, false);

        container.addView(cell);
    }

    private void showMenuCard(View anchorView) {
        LinearLayout cardLayout = new LinearLayout(this);
        cardLayout.setOrientation(LinearLayout.VERTICAL);
        cardLayout.setPadding(30, 30, 30, 30);
        cardLayout.setBackgroundColor(Color.WHITE);

        GradientDrawable background = new GradientDrawable();
        background.setCornerRadius(30f);
        background.setColor(Color.WHITE);
        background.setStroke(2, Color.LTGRAY);
        cardLayout.setBackground(background);

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

        optionText = "Conversão Simples";

        TextView option1 = new TextView(this);
        option1.setText(optionText);
        option1.setTextSize(18);
        option1.setPadding(20, 20, 20, 20);
        option1.setTextColor(Color.DKGRAY);
        option1.setClickable(true);

        option1.setOnClickListener(v -> {
            Intent intent = new Intent(ExtensionActivity.this, MainFunctionActivity.class);
            startActivity(intent);
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
            Intent intent = new Intent(ExtensionActivity.this, AcessActivity.class);
            startActivity(intent);
            popupWindow.dismiss();
        });

        cardLayout.addView(option3);

        popupWindow.showAtLocation(anchorView, Gravity.CENTER, 0, 0);
    }

    private void loadUnits(UnitOptions.grandeza grandeza) {
        String currentValue;
        if (activeDisplay != null)
            currentValue = ((TextView) activeDisplay.findViewById(R.id.value)).getText().toString();
        else
            currentValue = "0";
        container.removeAllViews();
        allDisplays.clear();
        displaysIds.clear();
        options = UnitOptions.getUnidades(grandeza);
        for (int i = 0; i < options.size() - 1; i++) {
            String option = options.get(i);
            addUnitCell(container, option, "0", UnitOptions.getAbbreviationFromNome(option));
            addLine(container);
        }
        addUnitCell(container, options.get(options.size() - 1), "0", UnitOptions.getAbbreviationFromNome(options.get(options.size() - 1)));
        if (!allDisplays.isEmpty()) {
            activeDisplay = allDisplays.get(0);
            activeDisplayIdx = 0;
            ((TextView) activeDisplay.findViewById(R.id.value)).setText(currentValue);
            updateDisplays();
        }
        for (View display : allDisplays) {
            display.setOnClickListener(v -> {
                activeDisplay = display;
                activeDisplayIdx = allDisplays.indexOf(display);
            });
        }
    }

    private void updateDisplays() {
        Enum<?> activeUnit = UnitOptions.getEnumFromNome(((TextView) activeDisplay.findViewById(R.id.unit_name)).getText().toString());
        for (View display : allDisplays) {
            if (!display.equals(activeDisplay)) {
                String unitName = ((TextView) display.findViewById(R.id.unit_name)).getText().toString();
                String result = OperationHandler.executeConversion(
                        grandezaAtual,
                        activeUnit,
                        UnitOptions.getEnumFromNome(unitName),
                        ((TextView) activeDisplay.findViewById(R.id.value)).getText().toString().replace(",", ".")
                );
                ((TextView) display.findViewById(R.id.value)).setText(result.replace(".", ","));
            }
        }
    }
}