using my.bookshop as my from '../db/data-model';

service CatalogService {
    entity Books as projection on my.Books;
//     @readonly entity readonlyBooks as projection on my.Books;
//     @insertonly entity insertonlyBooks2 as projection on my.Books;
//  @updateonly, @deleteonly 동작안함
//     @updateonly entity updateonlyBooks2 as projection on my.Books;
//     @deleteonly entity deleteonlyBooks2 as projection on my.Books;

    action addReview() returns {title : String};

    action accessDataBase(books : Books) returns {result : String};
}