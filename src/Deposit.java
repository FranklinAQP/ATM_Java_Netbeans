// Deposit.java
// Represents a deposit ATM transaction

public class Deposit extends Transaction
{
   private double amount; // amount to deposit
   private Keypad keypad; // reference to keypad
   private DepositSlot depositSlot; // reference to deposit slot
   private final static int CANCELED = 0; // constant for cancel option

   // Deposit constructor
   public Deposit( int userAccountNumber, Screen atmScreen, 
      BankDatabase atmBankDatabase, Keypad atmKeypad, 
      DepositSlot atmDepositSlot )
   {
      // initialize superclass variables
      super( userAccountNumber, atmScreen, atmBankDatabase );

      // initialize references to keypad and deposit slot
      keypad = atmKeypad;
      depositSlot = atmDepositSlot;
   } // end Deposit constructor

   // perform transaction
   public void execute()
   {
      BankDatabase bankDatabase = getBankDatabase(); // get reference
      Screen screen = getScreen(); // get reference

      amount = promptForDepositAmount(); // get deposit amount from user

      // check whether user entered a deposit amount or canceled
      if ( amount != CANCELED )
      {
         // request deposit envelope containing specified amount
         screen.displayMessage( 
            "\nPor favor, inserte el sobre conteniendo el deposito" );
         screen.displayDollarAmount( amount );
         screen.displayMessageLine( "." );

         // receive deposit envelope
         boolean envelopeReceived = depositSlot.isEnvelopeReceived();

         // check whether deposit envelope was received
         if ( envelopeReceived )
         {  
            screen.displayMessageLine( "\nSu sobre ha sido " +
               "recibido.\nNOTA: El dinero depositado no estara " +
               "disponible hasta que nosotros verifiquemos la cantidad " +
               "entonces se le hara el deposito." );
            
            // credit account to reflect the deposit
            bankDatabase.credit( getAccountNumber(), amount ); 
         } // end if
         else // deposit envelope not received
         {
            screen.displayMessageLine( "\nUd. no ha insertado un " +
               "sobre, por consiguiente el ATM ha cancelado su transaccion." );
         } // end else
      } // end if 
      else // user canceled instead of entering amount
      {
         screen.displayMessageLine( "\nCancelando transaccion..." );
      } // end else
   } // end method execute

   // prompt user to enter a deposit amount in cents 
   private double promptForDepositAmount()
   {
      Screen screen = getScreen(); // get reference to screen

      // display the prompt
      screen.displayMessage( "\nPor favor, ingrese una cantidad de deposito en " +
         "CENTIMOS (Pulse 0 para cancelar): " );
      int input = keypad.getInput(); // receive input of deposit amount
      
      // check whether the user canceled or entered a valid amount
      if ( input == CANCELED ) 
         return CANCELED;
      else
      {
         return ( double ) input / 100; // return dollar amount 
      } // end else
   } // end method promptForDepositAmount
} // end class Deposit



/**************************************************************************
 * (C) Copyright 1992-2007 by Deitel & Associates, Inc. and               *
 * Pearson Education, Inc. All Rights Reserved.                           *
 *                                                                        *
 * DISCLAIMER: The authors and publisher of this book have used their     *
 * best efforts in preparing the book. These efforts include the          *
 * development, research, and testing of the theories and programs        *
 * to determine their effectiveness. The authors and publisher make       *
 * no warranty of any kind, expressed or implied, with regard to these    *
 * programs or to the documentation contained in these books. The authors *
 * and publisher shall not be liable in any event for incidental or       *
 * consequential damages in connection with, or arising out of, the       *
 * furnishing, performance, or use of these programs.                     *
 *************************************************************************/