create database session16;
use session16;

create table User(
    id int primary key auto_increment,
    username varchar(50) not null ,
    password varchar(100) not null,
    email varchar(255),
    role enum('USER','ADMIN'),
    status bit default (1)
);

create table Bus(
    id int primary key auto_increment,
    licensePlate varchar(10) not null,
    busType enum('VIP', 'LUXURY','NORMAL'),
    rowSeat int not null ,
    colSeat int not null,
    totalSeat int,
    image varchar(255)
);

create table Seat(
    id int primary key auto_increment,
    nameSeat varchar(50) not null,
    price decimal(10,2) not null,
    busId int not null,
    status bit,
    foreign key (busId) references Bus(id)
);

create table BusTrip(
    id int primary key auto_increment,
    departurePoint varchar(100) not null,
    destination varchar(100) not null ,
    departureTime datetime ,
    arrivalTime datetime,
    busId int not null ,
    seatsAvailable int,
    image varchar(255),
    foreign key (busId) references Bus(id)
);

DELIMITER //
create procedure register(
    username_in varchar(50),
    password_in varchar(100),
    email_in varchar(255)
)
begin
    insert into User(username, password, email, role)
        values (username_in,password_in,email_in, 'USER');
end //
DELIMITER ;

DELIMITER //
create procedure login(
    username_in varchar(50),
    password_in varchar(100)
)
begin
    select * from User where username = username_in and password = password_in;
end //
DELIMITER ;

DELIMITER //
create procedure findUserByEmail(email_in varchar(255))
begin
    select * from User where email = email_in;
end //
DELIMITER ;

DELIMITER //
create procedure findUserByName(name_in varchar(50))
begin
    select * from User where username = name_in;
end //
DELIMITER ;

DELIMITER //
create trigger set_totalSeat_before_insert
before insert on Bus
for each row
    begin
        set NEW.totalSeat = NEW.rowSeat * NEW.colSeat;
    end //
DELIMITER ;

DELIMITER //
create procedure getAllProcedurePagination(
    page_size int,
    current_page int
)
begin
    declare offset_in int;
    set offset_in = (current_page - 1) * page_size;
    select * from BusTrip
        limit page_size
        offset offset_in;
end //
DELIMITER ;

DELIMITER //
create procedure totalBusTrip()
begin
    select count(*) from BusTrip;
end //
DELIMITER ;

DELIMITER //
    create procedure searchBusTrips(
        typeSearch_in varchar(50),
        keyword varchar(100),
        page_size int,
        current_page int
    )
begin
    declare offset_in int;
    set offset_in = (current_page - 1) * page_size;
    if typeSearch_in = 'START' then
        select * from BusTrip where departurePoint like concat('%',keyword,'%')
        limit page_size
        offset offset_in;
    else
        select * from BusTrip where destination like concat('%',keyword,'%')
        limit page_size
            offset offset_in;
    end if ;
end //
DELIMITER ;

DELIMITER //
create procedure totalSearch(
    typeSearch_in varchar(50),
    keyword varchar(100)
)
begin
    if typeSearch_in = 'START' then
        select count(*) from BusTrip where departurePoint like concat('%',keyword,'%');
    else
        select count(*) from BusTrip where destination like concat('%',keyword,'%');
    end if ;
end //
DELIMITER ;

DELIMITER //
create procedure getAllBus()
begin
    select * from Bus;
end //
DELIMITER ;

DELIMITER //
create procedure updateBus(
    id_in int,
    licensePlate_in varchar(10),
    busType_in enum('VIP', 'LUXURY','NORMAL'),
    image_in varchar(255)
)
begin
    update Bus
        set licensePlate = licensePlate_in,
            busType = busType_in,
            image = image_in
    where id = id_in;
end //
DELIMITER ;

DELIMITER //
create procedure createBus(
    licensePlate_in varchar(10),
    busType_in enum('VIP', 'LUXURY','NORMAL'),
    rowSeat_in int,
    colSeat_in int,
    image_in varchar(255)
)
begin
    declare busId int;
    declare rowIn int;
    declare colIn int;
    insert into Bus(licensePlate, busType, rowSeat, colSeat, image)
        values (licensePlate_in,busType_in,rowSeat_in,colSeat_in,image_in);

    set busId = last_insert_id();

    set rowIn = 1;
    while rowIn <= rowSeat_in do
        set colIn = 1;
        while colIn <= colSeat_in do
            insert into Seat (nameSeat, price, busId, status)
                values (
                        concat(char(64+rowIn),colIn),
                        case busType_in
                            when 'VIP' then 500000
                            when 'LUXURY' then 300000
                            ELSE 150000
                            END,
                        busId,
                        1
                       );
                    set colIn = colIn + 1;
            end while ;
        set rowIn = rowIn + 1;
        end while ;
end //
DELIMITER ;

DELIMITER //
create procedure deleteBus(id_in int)
begin
    delete from BusTrip where busId = id_in;
    delete from Seat where busId = id_in;
    delete from Bus where id = id_in;
end //
DELIMITER ;

DELIMITER //
create procedure getBusById(id_in int)
begin
    select * from Bus where id = id_in;
end //
DELIMITER ;

DELIMITER //
create procedure get_all_busTrips()
begin
    select * from BusTrip;
end //
DELIMITER ;

DELIMITER //
create procedure get_busTrip_by_id(
    in p_id int
)
begin
    select * from bustrip where id = p_id;
end //
DELIMITER ;

DELIMITER //
create procedure insert_busTrip(
    in p_departurepoint varchar(100),
    in p_destination varchar(100),
    in p_departuretime datetime,
    in p_arrivaltime datetime,
    in p_busid int,
    in p_seatsavailable int,
    in p_image varchar(255)
)
begin
    insert into bustrip(departurepoint, destination, departuretime, arrivaltime, busid, seatsavailable, image)
    values(p_departurepoint, p_destination, p_departuretime, p_arrivaltime, p_busid, p_seatsavailable, p_image);
end //
DELIMITER ;

DELIMITER //
create procedure update_busTrip(
    in p_id int,
    in p_departurepoint varchar(100),
    in p_destination varchar(100),
    in p_departuretime datetime,
    in p_arrivaltime datetime,
    in p_busid int,
    in p_seatsavailable int,
    in p_image varchar(255)
)
begin
    update bustrip
    set departurepoint = p_departurepoint,
        destination = p_destination,
        departuretime = p_departuretime,
        arrivaltime = p_arrivaltime,
        busid = p_busid,
        seatsavailable = p_seatsavailable,
        image = p_image
    where id = p_id;
end //
DELIMITER ;

DELIMITER //
create procedure delete_busTrip(
    in p_id int
)
begin
    delete from bustrip where id = p_id;
end //
DELIMITER ;

insert into User(username, password, email, role)
values ('Admin','Admin123','admin@gmail.com','ADMIN');

INSERT INTO Bus (licensePlate, busType, rowSeat, colSeat, totalSeat, image) VALUES
    ('51A-12345', 'NORMAL', 4, 4, 16, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSEtcp_ShMQzeTnJ2gHi1SHDiZTzB-Ii7lXuA&s'),
    ('51B-67890', 'VIP', 5, 5, 25, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSEtcp_ShMQzeTnJ2gHi1SHDiZTzB-Ii7lXuA&s'),
    ('51C-24680', 'LUXURY', 6, 5, 30, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSEtcp_ShMQzeTnJ2gHi1SHDiZTzB-Ii7lXuA&s'),
    ('51D-13579', 'NORMAL', 5, 4, 20, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSEtcp_ShMQzeTnJ2gHi1SHDiZTzB-Ii7lXuA&s'),
    ('51E-11223', 'VIP', 6, 6, 36, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSEtcp_ShMQzeTnJ2gHi1SHDiZTzB-Ii7lXuA&s');

INSERT INTO Seat (nameSeat, price, busId, status)
SELECT CONCAT('C', LPAD(n, 2, '0')), 150000, 3, b'1'
FROM (SELECT @row := @row + 1 AS n FROM (SELECT 0 UNION ALL SELECT 1 UNION ALL SELECT 2
                                         UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6
                                         UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) t1,
                                        (SELECT 0 UNION ALL SELECT 1 UNION ALL SELECT 2) t2, (SELECT @row := 0) r
      LIMIT 30) AS seats;

-- Ghế cho Bus 4: 5 x 4 = 20 ghế
INSERT INTO Seat (nameSeat, price, busId, status)
SELECT CONCAT('D', LPAD(n, 2, '0')), 100000, 4, b'1'
FROM (SELECT @row := @row + 1 AS n FROM (SELECT 0 UNION ALL SELECT 1 UNION ALL SELECT 2
                                         UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6
                                         UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) t1,
                                        (SELECT 0 UNION ALL SELECT 1 UNION ALL SELECT 2) t2, (SELECT @row := 0) r
      LIMIT 20) AS seats;

-- Ghế cho Bus 5: 6 x 6 = 36 ghế
INSERT INTO Seat (nameSeat, price, busId, status)
SELECT CONCAT('E', LPAD(n, 2, '0')), 140000, 5, b'1'
FROM (SELECT @row := @row + 1 AS n FROM (SELECT 0 UNION ALL SELECT 1 UNION ALL SELECT 2
                                         UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6
                                         UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) t1,
                                        (SELECT 0 UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3) t2, (SELECT @row := 0) r
      LIMIT 36) AS seats;

INSERT INTO BusTrip (departurePoint, destination, departureTime, arrivalTime, busId, seatsAvailable, image) VALUES
    ('Hà Nội', 'Hải Phòng', '2025-06-02 08:00:00', '2025-06-02 10:00:00', 1, 16, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSEtcp_ShMQzeTnJ2gHi1SHDiZTzB-Ii7lXuA&s'),
    ('Hà Nội', 'Ninh Bình', '2025-06-02 09:00:00', '2025-06-02 11:00:00', 1, 16, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSEtcp_ShMQzeTnJ2gHi1SHDiZTzB-Ii7lXuA&s'),
    ('Hồ Chí Minh', 'Vũng Tàu', '2025-06-02 07:30:00', '2025-06-02 10:00:00', 2, 25, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSEtcp_ShMQzeTnJ2gHi1SHDiZTzB-Ii7lXuA&s'),
    ('Hồ Chí Minh', 'Cần Thơ', '2025-06-02 08:30:00', '2025-06-02 12:00:00', 2, 25, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSEtcp_ShMQzeTnJ2gHi1SHDiZTzB-Ii7lXuA&s'),
    ('Đà Nẵng', 'Huế', '2025-06-02 06:00:00', '2025-06-02 08:00:00', 3, 30, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSEtcp_ShMQzeTnJ2gHi1SHDiZTzB-Ii7lXuA&s'),
    ('Đà Nẵng', 'Hội An', '2025-06-02 09:30:00', '2025-06-02 10:30:00', 3, 30, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSEtcp_ShMQzeTnJ2gHi1SHDiZTzB-Ii7lXuA&s'),
    ('Hà Nội', 'Sapa', '2025-06-02 22:00:00', '2025-06-03 06:00:00', 4, 20, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSEtcp_ShMQzeTnJ2gHi1SHDiZTzB-Ii7lXuA&s'),
    ('Hà Nội', 'Thanh Hóa', '2025-06-02 10:00:00', '2025-06-02 13:00:00', 4, 20, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSEtcp_ShMQzeTnJ2gHi1SHDiZTzB-Ii7lXuA&s'),
    ('Hồ Chí Minh', 'Đà Lạt', '2025-06-02 20:00:00', '2025-06-03 03:00:00', 5, 36, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSEtcp_ShMQzeTnJ2gHi1SHDiZTzB-Ii7lXuA&s'),
    ('Hồ Chí Minh', 'Phan Thiết', '2025-06-02 14:00:00', '2025-06-02 18:00:00', 5, 36, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSEtcp_ShMQzeTnJ2gHi1SHDiZTzB-Ii7lXuA&s');
