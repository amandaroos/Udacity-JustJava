package com.example.android.justjava;



import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int mQuantity = 1;
    int mPrice = 5;
    int mTotal = 0;

    CheckBox whippedCreamCheckbox;
    CheckBox chocolateCheckbox;
    EditText nameEditText;

    boolean hasWhippedCream = false;
    boolean hasChocolate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        whippedCreamCheckbox = findViewById(R.id.whipped_cream_checkbox);
        chocolateCheckbox = findViewById(R.id.chocolate_checkbox);
        nameEditText = findViewById(R.id.name_edit_text);
    }

    /**
     * This method increases mQuantity by 1.
     */
    public void increment(View view) {
        if (mQuantity < 30) {
            mQuantity++;
        }
        displayQuantity(mQuantity);
    }

    /**
     * This method decreases mQuantity by 1.
     */
    public void decrement(View view) {
        if (mQuantity > 1) {
            mQuantity--;
        }
        else {
            Toast.makeText(this,"You can't have less than 1 coffee", Toast.LENGTH_SHORT).show();
        }
        displayQuantity(mQuantity);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        mTotal = calculatePrice();
        String name = nameEditText.getText().toString();

        String priceMessage = createOrderSummary(name, mTotal);
        displayMessage(priceMessage);
    }

    /**
     * Calculates the price of the order
     */
    private int calculatePrice(){

        hasWhippedCream = whippedCreamCheckbox.isChecked();
        hasChocolate = chocolateCheckbox.isChecked();

        int price = mPrice;

        if(hasWhippedCream) {
            price += 1;
        }
        if (hasChocolate){
            price += 2;
        }

        price *= mQuantity;
        return price;
    }

    /**
     * Creates an order summary message
     * @param price the total price of an order
     * @return the order summary message
     */
    private String createOrderSummary(String name, int price){
        String subject = "Just Java order for " + name;

        String priceMessage = "Name: " + name;
        priceMessage += "\nAdd whipped cream? " + hasWhippedCream;
        priceMessage += "\nAdd chocolate? " + hasChocolate;
        priceMessage += "\nQuantity: " + mQuantity;
        priceMessage += "\nTotal: $" + price;
        priceMessage += "\nThank you!";

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        else{
            Log.v("MainActivity", "The intent wasn't sent");
        }

        return priceMessage;
    }

    /**
     * This method displays the given mQuantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = findViewById(R.id.quantity_text_view);
        quantityTextView.setText((Integer.toString(number)));
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }

}