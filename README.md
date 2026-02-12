# 🍣 Sushi Garden POS
A user-friendly management system for a Sushi restaurant, built with Java. It helps waiters take orders and allows managers to track daily sales.

✨ Features:
- Interactive Restaurant Map: Instantly see which tables are free and which are occupied.
- Easy Ordering: Click on a table, select sushi items from the visual menu, and send the order.
- Payment: Automatically calculates the total. The user selects the payment method (cash or card) and generates a receipt.
- Z-Report: At the end of the shift, the admin closes the day to see total revenue and the number of orders.

🔑 For testing:

You can use these PIN codes to test the different roles:
- Alexandra (Admin): PIN 9999 — Has access to the Z-Report and system closing.
- Mihai (Waiter): PIN 1234 — Can manage tables and take orders.

🛠️ Start the app
- Start the database:
  
  $ docker-compose up -d
- Open the project in your IDE (IntelliJ/Eclipse) and run the Main.java file.

📌 Future Adjustments

Currently, users and menu items are managed directly in the database. In the next versions, I plan to add:
- Staff Management: A screen to add or remove employees directly from the app.
- Menu Editor: A tool to update the menu.

