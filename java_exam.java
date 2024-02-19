
public interface DiscountRate {
    double getDiscountRate(CustomerType customerType, ProductType productType);
}

public enum CustomerType {
    PREMIUM, GOLD, SILVER, NORMAL
}

public enum ProductType {
    SERVICE, PRODUCT
}

public class Customer implements DiscountRate {
    private CustomerType customerType;

    public Customer(CustomerType customerType) {
        this.customerType = customerType;
    }

    @Override
    public double getDiscountRate(CustomerType customerType, ProductType productType) {
        if (this.customerType == customerType) {
            switch (productType) {
                case SERVICE:
                    switch (customerType) {
                        case PREMIUM:
                            return 0.2;
                        case GOLD:
                            return 0.15;
                        case SILVER:
                            return 0.1;
                        default:
                            return 0;
                    }
                case PRODUCT:
                    return 0.1; // Same discount for all customer types on products
                default:
                    return 0;
            }
        } else {
            return 0; // No discount for different customer types
        }
    }
}

public class Sale {
    private Customer customer;
    private ProductType productType;
    private double price;

    public Sale(Customer customer, ProductType productType, double price) {
        this.customer = customer;
        this.productType = productType;
        this.price = price;
    }

    public double getFinalPrice() {
        double discountRate = customer.getDiscountRate(customer.getCustomerType(), productType);
        double discountAmount = price * discountRate;
        return price - discountAmount;
    }

    public static void main(String[] args) {
        Customer customer1 = new Customer(CustomerType.PREMIUM);
        Customer customer2 = new Customer(CustomerType.GOLD);
        Customer customer3 = new Customer(CustomerType.SILVER);
        Customer customer4 = new Customer(CustomerType.NORMAL);

        Sale sale1 = new Sale(customer1, ProductType.SERVICE, 100);
        Sale sale2 = new Sale(customer2, ProductType.PRODUCT, 50);
        Sale sale3 = new Sale(customer3, ProductType.SERVICE, 200);
        Sale sale4 = new Sale(customer4, ProductType.PRODUCT, 75);

        System.out.printf("Final price for customer1 (Premium, Service): $%.2f\n", sale1.getFinalPrice());
        System.out.printf("Final price for customer2 (Gold, Product): $%.2f\n", sale2.getFinalPrice());
        System.out.printf("Final price for customer3 (Silver, Service): $%.2f\n", sale3.getFinalPrice());
        System.out.printf("Final price for customer4 (Normal, Product): $%.2f\n", sale4.getFinalPrice());
    }
}
