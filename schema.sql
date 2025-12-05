create table Product (
    id int primary key, 
    name varchar, 
    price float, 
    creation_datetime timestamp
);


create table Product_category (
    id int primary key, 
    name varchar, 
    product_id int references product(id)
);
