create table if not exists orders (
    id bigserial primary key,
    user_id varchar(255) not null,
    status varchar(50) not null,
    total_amount decimal,
    created_at timestamptz default current_timestamp
);

CREATE TABLE if not exists order_item (
    id bigserial primary key,
    product_id varchar(255) not null,
    product_name varchar(255) not null,
    price decimal not null,
    quantity integer not null,
    subtotal decimal not null,
    order_id bigint references orders(id) not null
);
