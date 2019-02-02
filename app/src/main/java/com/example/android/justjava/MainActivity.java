
/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.android.justjava;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

        CheckBox whippedCream = findViewById(R.id.whipped_cream);
        boolean addWhippedCream = whippedCream.isChecked();

        CheckBox chocolate= findViewById(R.id.chocolate);
        boolean addChocolate = chocolate.isChecked();

        EditText nameField = findViewById(R.id.name_field);
        String name = nameField.getText().toString();

        // calc price
        int price = calculatePrice(quantity,addWhippedCream, addChocolate);
        String priceMessage = createOrderSummary(price, addWhippedCream, addChocolate, name);
        //displayMessage(priceMessage);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.order_summary_email_subject, name));
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    /**
     * Create summary of the order.
     *
     * @param addWhippedCream is whether or not the user wants whipped cream topping
     * @param addChocolate is whether or not the user wants chocolate topping
     * @param name who order
     * @param price of the order
     * @return text summary
     */

    private String createOrderSummary(int price, boolean addWhippedCream, boolean addChocolate, String name) {


        String msg = getString(R.string.order_summary_name, name);
        msg += "\n" + getString(R.string.order_summary_whipped_cream, addWhippedCream);
        msg += "\n" + getString(R.string.order_summary_chocolate, addChocolate);
        msg += "\n" + getString(R.string.order_summary_quantity, quantity);
        msg += "\n" + getString(R.string.order_summary_price, price);
        msg += "\n" + getString(R.string.thank_you);

        return msg;

    }

    /**
     * Calculates the price of the order.
     *
     * @param quantity is the number of cups of coffee ordered
     */
    private int calculatePrice(int quantity, boolean addWhippedCream, boolean addChocolate ) {
        int price = quantity * 5;

        if (addWhippedCream) {
            price += quantity *1;
        }

        if (addChocolate) {
            price += quantity *2;
        }

        return price;
    }

    /**
     * This method is called when the '+' button is clicked.
     */
    public void increment(View view) {
        if (quantity == 100) {
            Toast.makeText(this, "You cannot have more than 100 coffees", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity += 1;
        displayQuantity(quantity);

    }

    /**
     * This method is called when the '+' button is clicked.
     */
    public void decrement(View view) {
        if (quantity<=1) {
            Toast.makeText(this, "You cannot have less than 1 coffees", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity -= 1;
        displayQuantity(quantity);

    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }


    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }
}
