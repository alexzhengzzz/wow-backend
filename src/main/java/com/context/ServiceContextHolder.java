package com.context;

public class ServiceContextHolder{
    private static final ThreadLocal<ServiceContext> holder = new ThreadLocal<>();
    private ServiceContextHolder(){}
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

    public static void clear() {
        holder.remove();
    }

}
