package com.demo2do.core.web.resolver;

/**
 * Pagination object
 *
 * @author David
 */
public class Page {

    /**
     * default records per page
     */
    public static final int DEFAULT_EVERY_PAGE = 10;

    public static final int DEFAULT_FETCHING_TIMES = 3;

    /**
     * the default current page
     */
    public static int DEFAULT_CURRENT_PAGE = 1;

    /**
     * imply if the page has previous page
     */
    private boolean prePage;

    /**
     * imply if the page has next page
     */
    private boolean nextPage;

    /**
     * the number of every page
     */
    private int everyPage;

    /**
     * the total page number
     */
    private int totalPage;

    /**
     * the number of current page
     */
    private int currentPage;

    /**
     * the begin index of the records by the current query
     */
    private int beginIndex;

    /**
     * the numbers of next pages to display
     */
    private int nextPages;

    /**
     * the numbers of previous page to display
     */
    private int prePages;

    /**
     * Default constructor
     */
    public Page() {

    }

    /**
     * Constructor using fields
     *
     * @param currentPage current page
     */
    public Page(int currentPage) {
        this.currentPage = currentPage == 0 ? DEFAULT_CURRENT_PAGE : currentPage;
        this.everyPage = DEFAULT_EVERY_PAGE;
        this.beginIndex = (this.currentPage - 1) * everyPage;
    }

    /**
     * The constructor using currentPage and everyPage
     *
     * @param currentPage current page
     * @param everyPage   every page
     */
    public Page(int currentPage, int everyPage) {
        this.currentPage = currentPage == 0 ? DEFAULT_CURRENT_PAGE : currentPage;
        this.everyPage = everyPage;
        this.beginIndex = (this.currentPage - 1) * everyPage;
    }

    /**
     * Calculate with resultRecords and totalRecords
     *
     * @param resultRecords the number of result records
     * @return page object
     */
    public Page calculate(int resultRecords) {

        this.totalPage = 0;

        // calculate prePage and prePages
        if (this.currentPage <= DEFAULT_FETCHING_TIMES) {
            this.prePages = this.currentPage - 1;
            this.prePage = false;
        } else if (this.currentPage == (DEFAULT_FETCHING_TIMES + 1)) {
            this.prePages = DEFAULT_FETCHING_TIMES;
            this.prePage = false;
        } else {
            this.prePages = DEFAULT_FETCHING_TIMES;
            this.prePage = true;
        }

        // calculate nextPage and nextPages
        if (resultRecords > (DEFAULT_FETCHING_TIMES * this.everyPage)) {
            this.nextPages = DEFAULT_FETCHING_TIMES - 1;
            this.nextPage = true;
        } else if (resultRecords == (DEFAULT_FETCHING_TIMES * this.everyPage)) {
            this.nextPages = DEFAULT_FETCHING_TIMES - 1;
            this.nextPage = false;
        } else if (resultRecords < (DEFAULT_FETCHING_TIMES * this.everyPage)) {
            this.nextPages = resultRecords == this.everyPage ? 0 : resultRecords / this.everyPage;
            this.nextPage = false;
        }

        return this;

    }

    /**
     * Calculate with resultRecords and totalRecords
     * <p/>
     * - With totalRecords >  0: display as entire mode
     * - With totalRecords == 0: display as simple mode
     *
     * @param resultRecords the number of result records
     * @param totalRecords  the number of total records
     * @return the page object
     */
    public Page calculate(int resultRecords, int totalRecords) {

        this.totalPage = (totalRecords % everyPage == 0) ? (totalRecords / everyPage) : (totalRecords / everyPage + 1);

        // The following two flags will be used in entire mode
        if (totalRecords > 0) {
            this.prePage = (currentPage == DEFAULT_CURRENT_PAGE) ? false : true;
            this.nextPage = (currentPage == totalPage || totalPage == 0) ? false : true;
        }

        return this;
    }

    /**
     * Set current page
     *
     * @param page The current page to set
     */
    public void setPage(int page) {
        this.currentPage = page;
    }

    /**
     * Get current page
     *
     * @return Return the current page
     */
    public int getPage() {
        return this.currentPage;
    }

    /**
     * @return Return the max result records
     */
    public int getMaxResultRecords() {
        return this.everyPage * DEFAULT_FETCHING_TIMES + 1;
    }

    /**
     * @return Returns the prePage
     */
    public boolean isPrePage() {
        return prePage;
    }

    /**
     * @return Returns the nextPage
     */
    public boolean isNextPage() {
        return nextPage;
    }

    /**
     * @return Returns the everyPage
     */
    public int getEveryPage() {
        return everyPage;
    }

    /**
     * @return Returns the totalPage
     */
    public int getTotalPage() {
        return totalPage;
    }

    /**
     * @return Returns the currentPage
     */
    public int getCurrentPage() {
        return currentPage;
    }

    /**
     * @return Returns the beginIndex
     */
    public int getBeginIndex() {
        return beginIndex;
    }

    /**
     * @return the nextPages
     */
    public int getNextPages() {
        return nextPages;
    }

    /**
     * @return the prePages
     */
    public int getPrePages() {
        return prePages;
    }

    /**
     * @param prePage The prePage to set.
     */
    public void setPrePage(boolean prePage) {
        this.prePage = prePage;
    }

    /**
     * @param nextPage The nextPage to set.
     */
    public void setNextPage(boolean nextPage) {
        this.nextPage = nextPage;
    }

    /**
     * @param everyPage The everyPage to set.
     */
    public void setEveryPage(int everyPage) {
        this.everyPage = everyPage;
    }

    /**
     * @param totalPage The totalPage to set.
     */
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    /**
     * @param currentPage The currentPage to set.
     */
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    /**
     * @param beginIndex The beginIndex to set.
     */
    public void setBeginIndex(int beginIndex) {
        this.beginIndex = beginIndex;
    }

    /**
     * @param nextPages the nextPages to set
     */
    public void setNextPages(int nextPages) {
        this.nextPages = nextPages;
    }

    /**
     * @param prePages the prePages to set
     */
    public void setPrePages(int prePages) {
        this.prePages = prePages;
    }

}
