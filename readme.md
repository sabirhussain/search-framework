# Search Framework
The goal of Search Framework is to make developer's life easy by allowing them to present data search/query in simple json form and let the API to convert this intent into actual query.
Underlying data provider could be RDBMS, NoSQL or Search Engine (Solr, Elastic Search)

## Sample JPA Implementation
I have tested this implementation with MySQL/H2. The provided sample is with H2 in the form of simple unit test case.

### Main Component
* org.search.api.jpa.JpaQueryBuilder
* org.search.api.jpa.JpaSearcher
* org.search.api.jpa.builder.condition.impl (condition builders)

### Demo Sample
Please see 'search-jpa' module test case - org.search.api.main.TestSearchApi
* org.search.api.service.StudentQueryBuilder - How to implement QueryBuilder for JPA entity.
* org.search.api.service.StudentSearcher - How to implement Searcher for JPA entity.
