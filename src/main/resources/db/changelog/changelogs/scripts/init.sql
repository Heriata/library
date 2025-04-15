insert into subscriptions(subscription_id, username, user_full_name, user_active, user_email)
values (1, 'amogus', 'Jane Doe', true, 'xxx@xxx.xx'),
       (2, 'aboba', 'John Doe', true, 'yyy@yyy.yy'),
       (3, 'sebeb', 'John Biba', true, 'aaa@aaa.aa'),
       (4, 'bruh', 'No Way', false, 'fff@fff.ff');

insert into books(book_id, book_name, book_author, publishing_date)
values (1, 'Java 8 The Complete Reference', 'Herbert Schildt', '01-14-2015'),
       (2, 'Book 1', 'Someone', '01-01-2020'),
       (3, 'Book 3', 'Someone', '01-01-2020'),
       (4, 'Book 4', 'Someone', '01-01-2020'),
       (5, 'Omagad', 'Hell Nah', '01-01-1488');

insert into entries(entry_id, subscription_id, book_id, taken_date)
values (1, 1, 1, now() - interval '25 day'),
       (2, 1, 2, now() - interval '19 day'),
       (3, 2, 3, now() - interval '10 day'),
       (4, 3, 4, now() - interval '22 day'),
       (5, 4, 5, now() - interval '7 day');