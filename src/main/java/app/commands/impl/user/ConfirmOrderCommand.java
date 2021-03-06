package app.commands.impl.user;

import app.commands.Command;
import app.commands.PageName;
import app.commands.ParameterName;
import app.model.entity.Dish;
import app.model.entity.Orders;
import app.model.entity.User;
import app.model.entity.Waiter;
import app.services.Services;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.util.List;

public class ConfirmOrderCommand implements Command {

    private static final Logger logger = LogManager.getLogger(ConfirmOrderCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        String page = PageName.ORDER;
        List<Dish> dishes = (List<Dish>) request.getSession().getAttribute(ParameterName.ORDER);
        if (dishes.isEmpty() ) {
            logger.error("You did't select anything");
            return page;
        }
        Orders order = new Orders();
        order.setListOrder(dishes);
        order.getTotal_price();
        long millis=System.currentTimeMillis();
        Date date = new Date(millis);
        order.setDate_time(date);
        User user = (User) request.getSession().getAttribute(ParameterName.USER);
        order.setUser_id(user.getId());
        Waiter waiter = (Waiter) request.getSession().getAttribute(ParameterName.WAITER);
        order.setWaiter_id(waiter.getId());
        Services.ORDERS_SERVICE.insert(order);
        request.getSession().setAttribute(ParameterName.ORDER_TO_PAY, order);
        request.getSession().removeAttribute(ParameterName.ORDER);
        return  PageName.PAY;
    }
}
