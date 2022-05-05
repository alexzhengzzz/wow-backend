-- for corp_employee table
create unique index corp_id_emp_id_index
    on wow.corp_employee (corp_id, employee_id);
-- for corporation table
create index corp_name_index
    on corporation (company_name);
-- for user table
constraint user_email_password_uindex
        unique (email, password),
-- for coupons table
create index coupons_batch_id_user_id_index
    on coupons (batch_id, user_id);