package coupon.exception;

public class CategoryNotFoundException extends CustomException {
    public CategoryNotFoundException(String category) {
        super(category + " is not a valid category name.");
    }
}
