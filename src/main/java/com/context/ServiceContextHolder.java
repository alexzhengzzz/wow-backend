package com.context;

/**
 * @author zhengmh
 * @Description
 * @date 2021/01/01 7:44 PM
 * Modified By:
 */
public class ServiceContextHolder {
    private static final ThreadLocal<ServiceContext> holder = new ThreadLocal<>();

    public static ServiceContext getServiceContext() {
        ServiceContext context = holder.get();
        if(context == null) {
            context = new ServiceContext();
            holder.set(context);
        }
        return context;
    }

    public static void setServiceContext(ServiceContext serviceContext) {
        holder.set(serviceContext);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        if(holder != null) {
            holder.remove();
        }
    }
}
