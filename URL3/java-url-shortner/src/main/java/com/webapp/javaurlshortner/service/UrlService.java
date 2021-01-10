package com.webapp.javaurlshortner.service;

/*

 */

import com.webapp.javaurlshortner.repository.UrlRepository; // Allow JPA CRUD
import com.webapp.javaurlshortner.entity.Url; // Entity of table in database
import com.webapp.javaurlshortner.dto.UrlLongRequest; // Data Transfer Object on POST Request
import com.webapp.javaurlshortner.common.URLValidator;

import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Date;


// @Services hold business logic.
// Apart from the fact that it is used to indicate that it's holding the business logic,
// there’s no noticeable specialty that this annotation provides.

//This is the main Service used by controller UrlController
@Service
public class UrlService {

    private final UrlRepository urlRepository;
    private final BaseConversionService conversion;

    // Parameterized Constructor to set UrlRepository and BaseConversionService references
    public UrlService(UrlRepository urlRepository, BaseConversionService baseConversion) {
        this.urlRepository = urlRepository;
        this.conversion = baseConversion;
    }

    // POST request containing LONG URL is "save" to database and short URL corresponding to Id in database is returned
    // Called by controller on POST method
    public String convertToShortUrl(UrlLongRequest request) throws Exception{
        // url entity object with scope of function
        var longUrl = request.getLongUrl();
        if(URLValidator.INSTANCE.validateURL(longUrl)) {
            var url = new Url();
            // setting parameters longUrl, ExpiresDate and CreatedDate
            url.setLongUrl(longUrl);
            url.setExpiresDate(request.getExpiresDate());
            url.setCreatedDate(new Date());
            // Java Persistence API
            var entity = urlRepository.save(url);

            // generate short URL from ID of entity
            var ShortURL = conversion.encodeToShortURL(entity.getId());

            // Return ShortURL
            return ShortURL;
        }
        else{
            throw new Exception("Please enter a valid URL");
        }
    }

    // Upon request from customer passing shortUrl, this function can return long URL which is used to redirect
    // Called by Controller on GET method
    public String getOriginalUrl(String shortUrl) {
        // Step 1. Convert Short URL to ID
        var id = conversion.decodeToNumber(shortUrl);
        System.out.println("urlService.getOriginalUrl decoded number is: "+id);

        // Step 2. Search database for Id. If Id is found set entity to row of data corresponding to it. Else Throw Error.
        var entity_ = urlRepository.findById(id);
        System.out.println("after assignment to entity:"+entity_);
        var entity = urlRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("There is no entity with " + shortUrl));

        // Step 3. Received an entity check if link is Expired ==> We can also use Event Scheduler to perform such jobs monthly/weekly
        if (entity.getExpiresDate() != null && entity.getExpiresDate().before(new Date())){
            urlRepository.delete(entity);
            throw new EntityNotFoundException("Link expired!");
        }

        // Step 4. As link is found and link not expired return it

        return entity.getLongUrl();
    }
}