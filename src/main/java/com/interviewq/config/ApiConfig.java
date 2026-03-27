package com.interviewq.config;

/**
 * Central place for API configuration.
 *
 * <p>Changing {@link #BASE_URL} is all that is needed to redirect the
 * entire suite to a different API.</p>
 *
 * <p><b>URL patterns</b>
 * <ul>
 *   <li>Paginated list : {@code GET <SEARCH_ENDPOINT>?pageNumber=0&pageSize=10}</li>
 *   <li>Single record  : {@code GET <BASE_ENDPOINT>?uid=<uid>}</li>
 * </ul>
 * </p>
 *
 * @see <a href="https://stapi.co/api/v1/rest/swagger-ui.html">STAPI Swagger UI</a>
 */
public class ApiConfig {

    // ---------------------------------------------------------------
    // Base URL – swap this to redirect the whole suite to another API
    // ---------------------------------------------------------------
    public static final String BASE_URL = "https://stapi.co/api/v1/rest";


    // ---------------------------------------------------------------
    // Default pagination params
    // ---------------------------------------------------------------
    public static final int DEFAULT_PAGE_NUMBER  = 0;
    public static final int DEFAULT_PAGE_SIZE    = 20;

    // ---------------------------------------------------------------
    // Characters & Beings
    // ---------------------------------------------------------------
    public static final String CHARACTER_SEARCH     = "/character/search";
    /** GET ?uid={uid} */
    public static final String CHARACTER            = "/character";

    public static final String ANIMAL_SEARCH        = "/animal/search";
    /** GET ?uid={uid} */
    public static final String ANIMAL               = "/animal";

    public static final String SPECIES_SEARCH       = "/species/search";
    /** GET ?uid={uid} */
    public static final String SPECIES              = "/species";

    // ---------------------------------------------------------------
    // TV Series, Seasons & Episodes
    // ---------------------------------------------------------------
    public static final String SERIES_SEARCH        = "/series/search";
    /** GET ?uid={uid} */
    public static final String SERIES               = "/series";

    public static final String SEASON_SEARCH        = "/season/search";
    /** GET ?uid={uid} */
    public static final String SEASON               = "/season";

    public static final String EPISODE_SEARCH       = "/episode/search";
    /** GET ?uid={uid} */
    public static final String EPISODE              = "/episode";

    // ---------------------------------------------------------------
    // Movies
    // ---------------------------------------------------------------
    public static final String MOVIE_SEARCH         = "/movie/search";
    /** GET ?uid={uid} */
    public static final String MOVIE                = "/movie";

    // ---------------------------------------------------------------
    // Comics
    // ---------------------------------------------------------------
    public static final String COMICS_SEARCH            = "/comics/search";
    /** GET ?uid={uid} */
    public static final String COMICS                   = "/comics";

    public static final String COMIC_COLLECTION_SEARCH  = "/comicCollection/search";
    /** GET ?uid={uid} */
    public static final String COMIC_COLLECTION         = "/comicCollection";

    public static final String COMIC_SERIES_SEARCH      = "/comicSeries/search";
    /** GET ?uid={uid} */
    public static final String COMIC_SERIES             = "/comicSeries";

    public static final String COMIC_STRIP_SEARCH       = "/comicStrip/search";
    /** GET ?uid={uid} */
    public static final String COMIC_STRIP              = "/comicStrip";

    // ---------------------------------------------------------------
    // Books, Magazines & Literature
    // ---------------------------------------------------------------
    public static final String LITERATURE_SEARCH        = "/literature/search";
    /** GET ?uid={uid} */
    public static final String LITERATURE               = "/literature";

    public static final String MAGAZINE_SEARCH          = "/magazine/search";
    /** GET ?uid={uid} */
    public static final String MAGAZINE                 = "/magazine";

    public static final String MAGAZINE_SERIES_SEARCH   = "/magazineSeries/search";
    /** GET ?uid={uid} */
    public static final String MAGAZINE_SERIES          = "/magazineSeries";

    // ---------------------------------------------------------------
    // Video Games & Releases
    // ---------------------------------------------------------------
    public static final String VIDEO_GAME_SEARCH        = "/videoGame/search";
    /** GET ?uid={uid} */
    public static final String VIDEO_GAME               = "/videoGame";

    public static final String VIDEO_RELEASE_SEARCH     = "/videoRelease/search";
    /** GET ?uid={uid} */
    public static final String VIDEO_RELEASE            = "/videoRelease";

    // ---------------------------------------------------------------
    // Soundtracks
    // ---------------------------------------------------------------
    public static final String SOUNDTRACK_SEARCH        = "/soundtrack/search";
    /** GET ?uid={uid} */
    public static final String SOUNDTRACK               = "/soundtrack";

    // ---------------------------------------------------------------
    // Trading Cards
    // ---------------------------------------------------------------
    public static final String TRADING_CARD_SEARCH      = "/tradingCard/search";
    /** GET ?uid={uid} */
    public static final String TRADING_CARD             = "/tradingCard";

    public static final String TRADING_CARD_DECK_SEARCH = "/tradingCardDeck/search";
    /** GET ?uid={uid} */
    public static final String TRADING_CARD_DECK        = "/tradingCardDeck";

    public static final String TRADING_CARD_SET_SEARCH  = "/tradingCardSet/search";
    /** GET ?uid={uid} */
    public static final String TRADING_CARD_SET         = "/tradingCardSet";

    // ---------------------------------------------------------------
    // Universe – Locations & Astronomy
    // ---------------------------------------------------------------
    public static final String ASTRONOMICAL_OBJECT_SEARCH = "/astronomicalObject/search";
    /** GET ?uid={uid} */
    public static final String ASTRONOMICAL_OBJECT       = "/astronomicalObject";

    public static final String LOCATION_SEARCH          = "/location/search";
    /** GET ?uid={uid} */
    public static final String LOCATION                 = "/location";

    // ---------------------------------------------------------------
    // Universe – Science & Nature
    // ---------------------------------------------------------------
    public static final String ELEMENT_SEARCH           = "/element/search";
    /** GET ?uid={uid} */
    public static final String ELEMENT                  = "/element";

    public static final String FOOD_SEARCH              = "/food/search";
    /** GET ?uid={uid} */
    public static final String FOOD                     = "/food";

    public static final String MATERIAL_SEARCH          = "/material/search";
    /** GET ?uid={uid} */
    public static final String MATERIAL                 = "/material";

    public static final String MEDICAL_CONDITION_SEARCH = "/medicalCondition/search";
    /** GET ?uid={uid} */
    public static final String MEDICAL_CONDITION        = "/medicalCondition";

    // ---------------------------------------------------------------
    // Universe – Society & Culture
    // ---------------------------------------------------------------
    public static final String CONFLICT_SEARCH          = "/conflict/search";
    /** GET ?uid={uid} */
    public static final String CONFLICT                 = "/conflict";

    public static final String OCCUPATION_SEARCH        = "/occupation/search";
    /** GET ?uid={uid} */
    public static final String OCCUPATION               = "/occupation";

    public static final String ORGANIZATION_SEARCH      = "/organization/search";
    /** GET ?uid={uid} */
    public static final String ORGANIZATION             = "/organization";

    public static final String TITLE_SEARCH             = "/title/search";
    /** GET ?uid={uid} */
    public static final String TITLE                    = "/title";

    // ---------------------------------------------------------------
    // Universe – Technology & Weapons
    // ---------------------------------------------------------------
    public static final String TECHNOLOGY_SEARCH        = "/technology/search";
    /** GET ?uid={uid} */
    public static final String TECHNOLOGY               = "/technology";

    public static final String WEAPON_SEARCH            = "/weapon/search";
    /** GET ?uid={uid} */
    public static final String WEAPON                   = "/weapon";

    public static final String SPACECRAFT_SEARCH        = "/spacecraft/search";
    /** GET ?uid={uid} */
    public static final String SPACECRAFT               = "/spacecraft";

    public static final String SPACECRAFT_CLASS_SEARCH  = "/spacecraftClass/search";
    /** GET ?uid={uid} */
    public static final String SPACECRAFT_CLASS         = "/spacecraftClass";

    // ---------------------------------------------------------------
    // Real-world People & Companies
    // ---------------------------------------------------------------
    public static final String PERFORMER_SEARCH         = "/performer/search";
    /** GET ?uid={uid} */
    public static final String PERFORMER                = "/performer";

    public static final String STAFF_SEARCH             = "/staff/search";
    /** GET ?uid={uid} */
    public static final String STAFF                    = "/staff";

    public static final String COMPANY_SEARCH           = "/company/search";
    /** GET ?uid={uid} */
    public static final String COMPANY                  = "/company";

    private ApiConfig() { /* utility class */ }
}
