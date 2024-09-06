package com.example.expensetracker;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;
import java.util.Random;

public class addCreditCard extends AppCompatActivity {
    int top = 290;
    public static ArrayList<CreditCard> myCreditCards = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the content view to the layout named opening_page.xml
        setContentView(R.layout.add_credit_card);
        Button addPopup = findViewById(R.id.callPopup);
        for (int i = 0; i < myCreditCards.size(); i++) {
            addCreditCardView(myCreditCards.get(i).getCardNumber(),myCreditCards.get(i).getExpireDate(),myCreditCards.get(i).getCardHolder(),myCreditCards.get(i).getCardHolder());
        }
        addPopup.setOnClickListener(v -> {
            // Inflate the popup layout
            View popupView = getLayoutInflater().inflate(R.layout.popup_add_card, null);
            PopupWindow popupWindow = new PopupWindow(popupView,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    true);

            // Set fade-in animation
            popupWindow.setAnimationStyle(R.style.PopupAnimation);

            // Show the popup window at the center of the parent layout
            popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);

            // Start the fade-in animation
            popupView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in));
            Button closeButton = popupView.findViewById(R.id.deleteButton);
            closeButton.setOnClickListener(view -> popupWindow.dismiss());
            EditText cardNumberEditText = popupView.findViewById(R.id.cardNumber);
            EditText expireDateEditText = popupView.findViewById(R.id.expireDate);
            EditText cvvEditText = popupView.findViewById(R.id.CVV);
            EditText nameEditText = popupView.findViewById(R.id.Name);
            Button addCardButton = popupView.findViewById(R.id.addCardButton);

            addCardButton.setOnClickListener(c -> {
                // Get input values
                String cardNumber = cardNumberEditText.getText().toString().trim();
                String expireDate = expireDateEditText.getText().toString().trim();
                String cvv = cvvEditText.getText().toString().trim();
                String nameOnCard = nameEditText.getText().toString().trim();

                // Do something with the input values, like displaying them or sending them to another activity
                // Example: Log the values
                addCreditCardView(cardNumber, expireDate, cvv, nameOnCard);
            });
        });

    }

    private void addCreditCardView(String userCardNumber, String userExpireDate, String userCVV, String userName) {
        // Inflate the CreditCard layout
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        RelativeLayout creditCardLayout = (RelativeLayout) inflater.inflate(R.layout.creditcardexemple, null);

        // Update the placeholders with user input
        TextView cardNumber = creditCardLayout.findViewById(R.id.tv_card_number);
        TextView expireDate = creditCardLayout.findViewById(R.id.tv_expire_date);
        TextView cvv = creditCardLayout.findViewById(R.id.tv_cvv_label);
        TextView name = creditCardLayout.findViewById(R.id.textView);

        cardNumber.setText(userCardNumber);
        expireDate.setText(userExpireDate);
        cvv.setText(userCVV);
        name.setText(userName);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        int topMarginPx = (int) (16 * getResources().getDisplayMetrics().density);
        int bottomMarginPx = (int) (16 * getResources().getDisplayMetrics().density);
        params.setMargins(0, topMarginPx, 0, bottomMarginPx);
        creditCardLayout.setLayoutParams(params);
        // Set random gradient background
        setRandomGradientBackground(creditCardLayout);
// Find the Delete button and set an OnClickListener
        Button deleteButton = creditCardLayout.findViewById(R.id.deleteCross);
        deleteButton.setOnClickListener(v -> {
            // Remove the credit card layout from its parent
            ViewGroup parent = (ViewGroup) creditCardLayout.getParent();
            if (parent != null) {
                parent.removeView(creditCardLayout);
            }
        });
        creditCardLayout.setOnClickListener(v -> {
            Intent intent = new Intent(addCreditCard.this, CreditCardDetail.class);
            startActivity(intent);
        });

        LinearLayout parentLayout = findViewById(R.id.spot); // Ensure this is the LinearLayout inside the ScrollView
        parentLayout.addView(creditCardLayout);
        myCreditCards.add(new CreditCard((String) cardNumber.getText(), (String) expireDate.getText(), (String) cvv.getText(), (String) name.getText()));
    }

    private void setRandomGradientBackground(RelativeLayout layout) {
        // Generate random colors
        int startColor = getRandomColor();
        int endColor = getRandomColor();

        // Load the gradient drawable using ContextCompat to resolve theme attributes
        GradientDrawable gradientDrawable = (GradientDrawable) getDrawable(R.drawable.rounded_rectangle);

        if (gradientDrawable != null) {
            // Set the new colors
            gradientDrawable.setColors(new int[]{startColor, endColor});

            // Apply the drawable as background
            layout.setBackground(gradientDrawable);
        }
    }

    private int getRandomColor() {
        Random random = new Random();
        return Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256));
    }
}
class CreditCard {
    private String cardNumber;
    private String cardHolder;
    private String expireDate;
    private String CVV;
    // Constructor, getters, and setters
    public CreditCard(String cardNumber,String expireDate,String CVV,String cardHolder) {
        this.cardNumber = cardNumber;
        this.cardHolder = cardHolder;
        this.expireDate = expireDate;
        this.CVV = CVV;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public String getUserCVV() {
        return CVV;
    }

    public String getExpireDate() {
        return expireDate;
    }
}