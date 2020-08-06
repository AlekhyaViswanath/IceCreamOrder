package com.example.icecreamorder;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
/**

 This app displays an order form to order icecream.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 0;
    private CheckBox chocolate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText text = (EditText) findViewById(R.id.name_field);
        String name = text.getText().toString();

        CheckBox Nuts = (CheckBox) findViewById(R.id.checkbox_Nuts);
        boolean hasNuts = Nuts.isChecked();

        CheckBox chocolate = (CheckBox) findViewById(R.id.checkbox_ChoChip);
        boolean haschocolate = chocolate.isChecked();

        CheckBox sprinkles = (CheckBox) findViewById(R.id.checkbox_sprinkles);
        boolean hassprinkles = sprinkles.isChecked();

        int price = calculatePrice(hasNuts, haschocolate,hassprinkles);
        String priceMessage = createOrderSummary(name, price, hasNuts, haschocolate,hassprinkles);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "IceCream order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * Calculates the price of the order.
     */
    private int calculatePrice(boolean addNuts, boolean addchocolate, boolean addsprinkles) {
        int basePrice = 5;

        if (addNuts) {
            basePrice += 1;
        }

        if (addchocolate) {
            basePrice += 2;
        }

        if(addsprinkles){
            basePrice += 3;
        }

        return quantity * basePrice;
    }

    /**
     * Creates summary of the order
     *
     * @param name
     * @param price
     * @param hasNuts
     * @param haschocolate
     * @param hassprinkles
     * @return
     */

    private String createOrderSummary(String name, int price, boolean hasNuts, boolean haschocolate, boolean hassprinkles) {

        String priceMessage = "\n Order_summary_name:\t"+ name;
        priceMessage += "\nQuantity : " + quantity;
        priceMessage += "\nAdd Nuts?\t" + hasNuts;
        priceMessage += "\nAdd Chocolate?\t" + haschocolate;
        priceMessage += "\nAdd Sprinkles?\t" + hassprinkles;
        priceMessage += "\nTotal: $" + price;
        priceMessage = priceMessage + "\n\nThank_you:)";
        return priceMessage;
    }

    public void increment(View view) {
        if (quantity == 100) {
            Toast.makeText(this, "You cannot have more than 100 icecreams", Toast.LENGTH_SHORT).show();
            return;
        }

        quantity = quantity + 1;
        display(quantity);
    }

    public void decrement(View view) {
        if (quantity == 1) {
            Toast.makeText(this, "You cannot have less than 1 icecream", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity - 1;
        display(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

}


