package br.com.online.library.model.Book;

public enum BookCategory {
    SCIENCE_FICTION("Science Fiction"),
    FANTASY("Fantasy"),
    MYSTERY("Mystery"),
    THRILLER("Thriller"),
    ROMANCE("Romance"),
    HISTORICAL_FICTION("Historical Fiction"),
    BIOGRAPHY("Biography"),
    AUTOBIOGRAPHY("Autobiography"),
    MEMOIR("Memoir"),
    ESSAY("Essay"),
    SELF_HELP("Self-Help"),
    TRUE_CRIME("True Crime"),
    PICTURE_BOOKS("Picture Books"),
    MIDDLE_GRADE("Middle Grade"),
    YOUNG_ADULT("Young Adult"),
    POETRY("Poetry"),
    FREE_VERSE("Free Verse"),
    SONNET("Sonnet"),
    HAIKU("Haiku"),
    DRAMA("Drama"),
    PLAYS("Plays"),
    COMIC_STRIPS("Comic Strips"),
    GRAPHIC_NOVELS("Graphic Novels"),
    POPULAR_SCIENCE("Popular Science"),
    NATURE_WRITING("Nature Writing"),
    TRAVELOGUE("Travelogue"),
    RELIGIOUS_TEXTS("Religious Texts"),
    SPIRITUALITY("Spirituality"),
    COOKBOOKS("Cookbooks"),
    CULINARY_LITERATURE("Culinary Literature"),
    HISTORY_BOOKS("History Books"),
    HORROR("Horror"),
    SUPERNATURAL_HORROR("Supernatural Horror"),
    SATIRICAL_FICTION("Satirical Fiction"),
    DYSTOPIAN_NOVELS("Dystopian Novels"),
    EROTICA("Erotica"),
    MARTIAL_ARTS_FICTION("Martial Arts Fiction"),
    INSTRUCTION_MANUAL("Instruction Manual"),
    TECHNOLOGY("Technology"),
    OTHER("Other");

    private final String categoryName;

    BookCategory(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getName() {
        return categoryName;
    }
}
