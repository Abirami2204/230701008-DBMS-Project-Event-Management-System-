CREATE TABLE events_details (
    event_id INT NOT NULL AUTO_INCREMENT,
    event_name VARCHAR(255) NOT NULL,
    event_date DATE NOT NULL,
    ticket_price DOUBLE DEFAULT NULL,
    PRIMARY KEY (event_id)
);

Select * from event_details;
