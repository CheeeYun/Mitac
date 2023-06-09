package payroll;

public class OrderNotFoundException extends RuntimeException{
    OrderNotFoundException(long id){
        super("Sorry Order not found! id="+id);
    }
}
