public class CatFoodBowl {
    private static final int BOWL_CAPACITY = 700;
    private static int brownCatWellFedCount = 0;
    private static int blackCatWellFedCount = 0;

    private int currentAmount = BOWL_CAPACITY;

    enum CatColor {BROWN, BLACK}

    synchronized boolean eat(int amount, CatColor who) {
        System.out.println(    "Bowl now has    " + currentAmount + " units of food");
        if (currentAmount >= amount) {
            currentAmount -= amount;
            System.out.println("Bowl now has    " + currentAmount + " units of food - " + who + " have eaten " + amount + " units");
            return true;
        } else {
            System.out.println("Bowl still have " + currentAmount + " units of food - " + who + " failed to eat");
            return false;
        }
    }

    class HungryCat implements Runnable {
        private CatColor color;
        private static final int EAT_AMOUNT = 200;

        HungryCat(CatColor color) {
            this.color = color;
        }

        @Override
        public void run() {
            // eat for the first time and try the second only if first was successful
            if (eat(EAT_AMOUNT, color))
                if (eat(EAT_AMOUNT, color))
                    switch (color) {
                        case BLACK -> blackCatWellFedCount++;
                        case BROWN -> brownCatWellFedCount++;
                    }

        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        Thread blc = new Thread(new HungryCat(CatColor.BLACK));
        Thread brc = new Thread(new HungryCat(CatColor.BROWN));

        blc.start();
        brc.start();

        try {
            blc.join();
            brc.join();
        }
        catch (InterruptedException e) {e.printStackTrace();}
        return "Current stats: BLACK: " + blackCatWellFedCount + ", BROWN: " + brownCatWellFedCount+", brown to total ratio: "+ String.format("%2.02f", ((float) brownCatWellFedCount / (brownCatWellFedCount+blackCatWellFedCount)));


    }
}
