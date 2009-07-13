class WebthumbsController {

    def webthumbsService
    /*
        This is just an example. You should never expose an action that takes any URL
        in your own controllers as you would be offering a FREE service to others who find out.
    */
    def index = { render webthumbsService.getThumbURL('http://www.groovytweets.org') }
}
