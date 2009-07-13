import org.codehaus.groovy.grails.commons.ConfigurationHolder
import java.security.MessageDigest
import java.net.URLEncoder

class WebthumbsService {

    boolean transactional = false

    def getThumbURL(url, size="medium2", cache=1) {
        if (!url) return null

        def key = ConfigurationHolder.config.webthumbs.key
        def user = ConfigurationHolder.config.webthumbs.user

        def rawURL = (url.startsWith('https') ? url.substring(8) : url.substring(7))
        def dateString = new Date().format('yyyyMMdd')
        def hexMD5Hash = getHexMD5(dateString + rawURL + key)
        def encodedURL = URLEncoder.encode(rawURL, 'UTF-8')

        def blugaBase = 'http://webthumb.bluga.net/easythumb.php?'
        def imageURL = "${blugaBase}user=${user}&url=${encodedURL}&hash=${hexMD5Hash}&cache=${cache}&size=${size}"
        log.debug("Thumbs URL for ${url}->${imageURL}")
        return imageURL
    }

    //32 character long hex md5 hash
    def getHexMD5(raw)
    {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.reset()
        md.update(raw as byte[]);
        byte[] digest = md.digest()

        BigInteger bigInt = new BigInteger(1,digest);
        String hashtext = bigInt.toString(16);

        //zero pad it to get the full 32 chars.
        hashtext = hashtext.padLeft(32, "0")
        return hashtext
    }
}
