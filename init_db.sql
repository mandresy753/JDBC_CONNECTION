create database product_management_db; --création db
create user product_manager_user with password '123456'; --création user
--permission création tables sur db
grant create on database product_management_db to product_manager_user;
\c product_management_db --entrer dans le db
--permission crud sur les tables --> exixtantes
grant insert, select, update, delete on all tables 
    in schema public to product_manager_user;
--changer permission par défaut des tables  --> crud
alter default privileges in schema public
    grant insert, select, update, delete on tables 
    in schema public to product_manager_user;
