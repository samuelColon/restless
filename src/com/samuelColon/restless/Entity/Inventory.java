package com.samuelColon.restless.Entity;


public class Inventory {
    /** enum list of items */

    /**
     * current items in inventory
     */

    private Item[] currentItems;
    private Item[] keyItems;

    /**
     * receives inventory item objects, not just the strings
     */
    //    public ArrayList<Item> currentItems;
    //    public Inventory(ArrayList<Item> currentItems) {
    //        this.currentItems = currentItems;
    //    }
    public Inventory ( Item[] currentItems, Item[] keyItems ) {
        this.currentItems = currentItems;
        this.keyItems = keyItems;
    }

    /**
     * inventory is empty( most likely a new game )
     */
    public Inventory() {
        currentItems = new Item[ 20 ]; // max of 20 items
        keyItems     = new Item[ 5 ];
    }

    public void useItem (String item) {
        System.out.println( "pretend to use the " + item );
        currentItems[ 1 ] = null;
    }

    public void buyItem() {
        System.out.println( "pretend to buy an item" );
    }

    public boolean addItem( Item item ) {
        currentItems[ 0 ] = item;
        return true;
    }

    public boolean dropItem( Item item ) {
        currentItems[ 1 ] = null;
        return true;
    }

    /**
     * inventory will determine the value of item being sold and return it to the calling object
     */
    private int itemValue = 0;

    public int sellItem( String item ) {
        return itemValue;
    }

    /**
     * caution debugging bellow
     */
    public void printItems() {
        for ( int i = 0; i < currentItems.length; i++ ) {
            Item temp = currentItems[ i ];
            if ( temp != null ) {
                System.out.println( temp.getItemType() );
            }
        }
    }

    public void doAction( int a ) {
        switch ( a ) {
            case 1:
                buyItem();
                break;
            case 2:
                useItem( "potion" );
                break;
        }
    }
}