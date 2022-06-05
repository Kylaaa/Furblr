package com.itreallyiskyler.furblr.networking.models

import com.itreallyiskyler.furblr.enum.*
import com.itreallyiskyler.furblr.util.DateFormatter
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.system.measureTimeMillis

class PagePostDetailsUnitTest {
    private val EXAMPLE_BODY_IMAGE : String = """
    <html lang="en" class="" xmlns="http://www.w3.org/1999/xhtml"><head></head>

    <!-- EU request: no -->
    <body data-static-path="/themes/beta" id="pageid-submission">

        <!-- sidebar -->
        <div class="mobile-navigation">
            <div class="mobile-nav-container">
                <div class="mobile-nav-container-item left">
                    <label for="mobile-menu-nav" class="css-menu-toggle only-one"><img class="burger-menu" src="/themes/beta/img/fa-burger-menu-icon.png"></label>
                </div>
                <div class="mobile-nav-container-item center"><a class="mobile-nav-logo" href="/"><img class="site-logo" src="/themes/beta/img/banners/fa_logo.png?v2"></a></div>
            </div>
        </div>

        <div id="main-window" class="footer-mobile-tweak g-wrapper">
            <div id="site-content">
            <!-- /header -->
            <div id="submission_page" class="page-content-type-image">
                <div id="columnpage">
                    <div class="submission-sidebar">

                        <section class="stats-container text">
                            <div class="views">
                                <span class="font-large">8</span><br>
                                <span class="font-small highlight">Views</span>
                            </div>
                    
                            <div class="comments">
                                <span class="font-large">0</span><br>
                                <span class="font-small highlight">Comments</span>
                            </div>
                        
                            <div class="favorites">
                                <span class="font-large">0</span><br>
                                <span class="font-small highlight">Favorites</span>
                            </div>
                        
                            <div class="rating">
                                <span class="font-large rating-box inline general"> General</span><br>
                                <span class="font-small highlight">Rating</span>
                            </div>
                        </section>

                        <section class="buttons">
                            <div class="fav"><a href="/fav/47525111/?key=263aecbeb29aed6793c16f7748f9f07a89a6bda1">+ Fav</a></div>
                            <div class="download"><a href="//d.furaffinity.net/art/nimfaeya/1654462809/1654462809.nimfaeya_2016-07-26.png">Download</a></div>
                            <div class="note"><a href="/newpm/nimfaeya/">Note</a></div>
                        </section>

                        <section class="info text">
                            <div><strong class="highlight">Category</strong> <div><span class="category-name">All</span> / <span class="type-name">All</span></div></div>            <div><strong class="highlight">Species</strong> <span>Unspecified / Any</span></div>            <div><strong class="highlight">Gender</strong> <span>Any</span></div>            <div><strong class="highlight">Size</strong> <span>1024 x 1280</span></div>
                        </section>

                        <section class="tags-row">
                            <span class="tags"><a href="/search/@keywords 2016">2016</a></span>
                            <span class="tags"><a href="/search/@keywords gaiaonline">gaiaonline</a></span>
                            <span class="tags"><a href="/search/@keywords the">the</a></span>
                            <span class="tags"><a href="/search/@keywords black">black</a></span>
                            <span class="tags"><a href="/search/@keywords tea">tea</a></span>
                            <span class="tags"><a href="/search/@keywords geisha">geisha</a></span>
                        </section>

    <section class="shinies-promo text">
    <div class="shinies-promo-star">★</div>
    <div class="shinies-promo-text"><a href="/user/nimfaeya/#tip">Like nimfaeya's stuff? Support them by sending some Shinies their way!</a></div>
    </section>

    <section class="minigallery-more">
    <h3>See more from <a href="/gallery/nimfaeya/">nimfaeya</a></h3>

    <div class="preview-gallery hideonmobile">
    <div class="preview-gallery-container">
    <a href="/view/47525028/"><img class="preview-gallery-image" title="Nyxlii Summer" src="//t.furaffinity.net/47525028@200-1654462535.jpg"></a>
    </div>
    <div class="preview-gallery-container">
    <a href="/view/47524990/"><img class="preview-gallery-image" title="Nyxlii Donut" src="//t.furaffinity.net/47524990@200-1654462425.jpg"></a>
    </div>
    <div class="preview-gallery-container">
    <a href="/view/47524934/"><img class="preview-gallery-image" title="FallenAngelofMurder" src="//t.furaffinity.net/47524934@200-1654462226.jpg"></a>
    </div>
    <div class="preview-gallery-container">
    <a href="/view/47524893/"><img class="preview-gallery-image" title="Panda Burgers • Nyxlii" src="//t.furaffinity.net/47524893@200-1654462104.jpg"></a>
    </div>
    <div class="preview-gallery-container">
    <a href="/view/47524832/"><img class="preview-gallery-image" title="Panda Burgers" src="//t.furaffinity.net/47524832@200-1654461918.jpg"></a>
    </div>
    <div class="preview-gallery-container">
    <a href="/view/47524763/"><img class="preview-gallery-image" title="Rozenhiem Defeat" src="//t.furaffinity.net/47524763@200-1654461616.jpg"></a>
    </div>
    </div>
    </section>

    <section class="folder-list-container text">
    <h3>Listed in Folders</h3>

    <div>
    <a href="/gallery/nimfaeya/folder/1116565/COMMISSION/" title="90 submissions" class="dotted">
    <strong>•   •   •</strong> -
    <span>〖 COMMISSION 〗</span>
    </a>
    </div>
    <div>
    <a href="/gallery/nimfaeya/folder/1116595/2016/" title="61 submissions" class="dotted">
    <strong>☆</strong> -
    <span>〖 2016 〗</span>
    </a>
    </div>

    </section>


    </div>

    <div class="submission-content">


    <div class="leaderboardAd">
    <div data-id="header_middle" class="leaderboardAd__slot format--leaderboard jsAdSlot hidden"></div>
    </div>

    <div class="aligncenter submission-area">
    <img id="submissionImg" title="Click to change the View" alt="The Black Tea Geisha VII" data-fullview-src="//d.furaffinity.net/art/nimfaeya/1654462809/1654462809.nimfaeya_2016-07-26.png" data-preview-src="//t.furaffinity.net/47525111@400-1654462809.jpg" src="//d.furaffinity.net/art/nimfaeya/1654462809/1654462809.nimfaeya_2016-07-26.png" style="cursor: pointer;">
    </div>


    <div class="aligncenter auto_link hideonfull1 favorite-nav">

    <a class="button standard mobile-fix" href="/fav/47525111/?key=263aecbeb29aed6793c16f7748f9f07a89a6bda1">+Fav</a>

    <a class="button standard mobile-fix" href="/gallery/nimfaeya/" title="194 submissions">Main Gallery</a>
    <a class="button standard mobile-fix" href="//d.furaffinity.net/art/nimfaeya/1654462809/1654462809.nimfaeya_2016-07-26.png">Download</a>

    <a class="button standard mobile-fix" href="/newpm/nimfaeya/">Note</a>
    <a href="/view/47525028/" class="button standard mobile-fix">Next</a>
    </div>

    <section>
    <div class="section-header">
    <div class="submission-id-container">
    <div class="submission-id-avatar">
    <a href="/user/nimfaeya/"><img class="submission-user-icon floatleft avatar" src="//a.furaffinity.net/1650227972/nimfaeya.gif"></a>
    </div>

    <div class="submission-id-sub-container">
    <div class="submission-title">
    <h2><p>The Black Tea Geisha VII</p></h2>
    </div>
    By <a href="/user/nimfaeya/"><strong>nimfaeya</strong></a>,
    <span class="hideontablet hideondesktop"><br> </span>
    <span class="hideonmobile">posted </span>
    <strong><span title="Jun 5, 2022 05:00 PM" class="popup_date">some seconds ago</span></strong>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;nim&nbsp;|&nbsp;<a href="/user/nimfaeya/#tip">Support me with Shinies!</a>                    </div>
    </div>
    </div>
    <div class="section-body">
    <div class="submission-description user-submitted-links">
    <code class="bbcode bbcode_center"><br>
    •    •    •<br>
    <br>
    【 t o :  The Black Tea Geisha】<br>
    <br>
    •    •    •<br>
    2016<br>
    </code>
    <div class="submission-footer">
    <hr>
    <code class="bbcode bbcode_center"><br>
    •   •   •<br>
    <a href="/user/nimfaeya" class="iconusername"><img src="//a.furaffinity.net/20220605/nimfaeya.gif" align="middle" title="nimfaeya" alt="nimfaeya"></a> <a href="/user/nyxlii" class="iconusername"><img src="//a.furaffinity.net/20220605/nyxlii.gif" align="middle" title="nyxlii" alt="nyxlii"></a> <a href="/user/faughn" class="iconusername"><img src="//a.furaffinity.net/20220605/faughn.gif" align="middle" title="faughn" alt="faughn"></a> <a href="/user/lily-fay" class="iconusername"><img src="//a.furaffinity.net/20220605/lily-fay.gif" align="middle" title="Lily-Fay" alt="Lily-Fay"></a><br>
    </code>                        </div>
    </div>
    </div>

    <!-- mobile view -->
    <div class="section-footer section-footer-padding hideonfull alignright">
    <div class="submission-stats-container">
    <div class="views">
    <span class="font-large">8</span><br>
    <span class="font-small highlight">Views</span>
    </div>

    <div class="favorites">
    <span class="font-large">0</span><br>
    <span class="font-small highlight">Favorites</span>
    </div>

    <div class="comments">
    <span class="font-large">0</span><br>
    <span class="font-small highlight">Comments</span>
    </div>

    <div class="rating">
    <span class="font-large rating-box inline general"> General</span><br>
    <span class="font-small highlight">Rating</span>
    </div>
    </div>
    </div>
    </section>

    <!-- mobile view -->
    <div class="hideonfull">
    <section class="stats-mobile">
    <div class="section-body info">
    <div><strong class="highlight">Category</strong> <span class="category-name">All</span> / <span class="type-name">All</span></div>                    <div><strong class="highlight">Species</strong> <span>Unspecified / Any</span></div>                    <div><strong class="highlight">Gender</strong> <span>Any</span></div>                    <div><strong class="highlight">Size</strong> <span>1024 x 1280px</span></div>                </div>
    </section>

    <section class="tags-mobile">
    <div class="section-body">
    <span class="tags"><a href="/search/@keywords 2016">2016</a></span>
    <span class="tags"><a href="/search/@keywords gaiaonline">gaiaonline</a></span>
    <span class="tags"><a href="/search/@keywords the">the</a></span>
    <span class="tags"><a href="/search/@keywords black">black</a></span>
    <span class="tags"><a href="/search/@keywords tea">tea</a></span>
    <span class="tags"><a href="/search/@keywords geisha">geisha</a></span>
    </div>
    </section>

    <section class="folders-mobile">
    <div class="section-body">
    <h3>Listed in Folders</h3>

    <div>
    <a href="/gallery/nimfaeya/folder/1116565/COMMISSION/" title="90 submissions" class="dotted">
    <strong>•   •   •</strong> -
    <span>〖 COMMISSION 〗</span>
    </a>
    </div>
    <div>
    <a href="/gallery/nimfaeya/folder/1116595/2016/" title="61 submissions" class="dotted">
    <strong>☆</strong> -
    <span>〖 2016 〗</span>
    </a>
    </div>
    </div>
    </section>


    </div>

    <div class="comments-list">


    <div id="responsebox" class="aligncenter">
    <form name="myform" method="post" action="/view/47525111/" id="add_comment_form">
    <div class="section-body no-padding">
    <input type="hidden" name="f" value="0">
    <input type="hidden" name="action" value="reply" id="form-action">
    <input type="hidden" name="replyto" id="form-replyto" value="">
    <textarea id="JSMessage" name="reply" class="textarea textarearesize" placeholder="Type your comment here."></textarea>
    </div>

    <div class="section-footer alignright">
    <span class="floatleft" style="padding: 7px 0 0 0"><i class="bbcodeformat b hand" title="Bold (CTRL+B)" onclick="performInsert(this, '[b]', '[/b]');"></i>
    <i class="bbcodeformat i hand" title="Italic (CTRL+I)" onclick="performInsert(this, '[i]', '[/i]');"></i>
    <i class="bbcodeformat u hand" title="Underlined (CTRL+U)" onclick="performInsert(this, '[u]', '[/u]');"></i>
    &nbsp;&nbsp;&nbsp;
    <i class="bbcodeformat align_left hand" title="Align Left" onclick="performInsert(this, '[left]', '[/left]');"></i>
    <i class="bbcodeformat align_center hand" title="Align Center" onclick="performInsert(this, '[center]', '[/center]');"></i>
    <i class="bbcodeformat align_right hand" title="Align Right" onclick="performInsert(this, '[right]', '[/right]');"></i>
    </span>
    <button class="go post-comment" type="submit" name="submit" value="Post Comment">Post Comment</button>
    </div>
    </form>
    </div>
    </div>
    </div>
    </div>
    </div>
    </div>
    </div>
    </div>
    </body></html>
    """
    private val EXAMPLE_BODY_AUDIO : String = """
            <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
            <html lang="en" class="no-js" xmlns="http://www.w3.org/1999/xhtml">

            <head>
                <meta charset="utf-8" />
                <title>AVISHKA PARADISE by LameCheez -- Fur Affinity [dot] net</title>
            </head>

            <!-- EU request: no -->
            <body data-static-path="/themes/beta" id="pageid-submission">
                <div id="main-window" class="footer-mobile-tweak g-wrapper">
                    <div id="header">
                        <a href="/"><div class="site-banner site-banner-positioning FlexEmbed hideonmobile"></div></a>
                        <a name="top"></a>
                    </div>
                    <div id="site-content">
                        <!-- /header -->
                        <div id="submission_page" class="page-content-type-music">
                            <div id="columnpage">
                                <div class="submission-sidebar">
                                    <section class="stats-container text">
                                        <div class="views">
                                            <span class="font-large">69</span><br>
                                            <span class="font-small highlight">Views</span>
                                        </div>
                
                                        <div class="comments">
                                            <span class="font-large">0</span><br>
                                            <span class="font-small highlight">Comments</span>
                                        </div>
                
                                        <div class="favorites">
                                            <span class="font-large">0</span><br>
                                            <span class="font-small highlight">Favorites</span>
                                        </div>
                
                                        <div class="rating">
                                            <span class="font-large rating-box inline general"> General</span><br>
                                            <span class="font-small highlight">Rating</span>
                                        </div>
                                    </section>

                                    <section class="buttons">
                                        <div class="fav"><a href="/fav/46683183/?key=b3d836ea3258f7c05baeafa70a8ecd6aea69406b">+ Fav</a></div>
                                        <div class="download"><a href="//d.furaffinity.net/art/lamecheez/music/1649367062/1649367062.lamecheez_информатика.mp3">Download</a></div>
                                        <div class="note"><a href="/newpm/lamecheez/">Note</a></div>
                                    </section>

                                    <section class="info text">
                                        <div><strong class="highlight">Category</strong> <div><span class="category-name">Music</span> / <span class="type-name">All</span></div></div>
                                        <div><strong class="highlight">Species</strong> <span>Unspecified / Any</span></div>
                                        <div><strong class="highlight">Gender</strong> <span>Any</span></div>
                                        <div><strong class="highlight">Size</strong> <span>120 x 120</span></div>
                                    </section>
                
                                    <section class="tags-row">
                                        <span class="tags"><a href="/search/@keywords Experimental">Experimental</a></span>
                                        <span class="tags"><a href="/search/@keywords Hip-Hop">Hip-Hop</a></span>
                                        <span class="tags"><a href="/search/@keywords Digicore">Digicore</a></span>
                                        <span class="tags"><a href="/search/@keywords Surge">Surge</a></span>
                                        <span class="tags"><a href="/search/@keywords Minimalistic">Minimalistic</a></span>
                                        <span class="tags"><a href="/search/@keywords Trap">Trap</a></span>
                                        <span class="tags"><a href="/search/@keywords Detroit">Detroit</a></span>
                                        <span class="tags"><a href="/search/@keywords Alternative">Alternative</a></span>
                                        <span class="tags"><a href="/search/@keywords Trap">Trap</a></span>
                                    </section>

                                    <section class="minigallery-more">
                                        <h3>See more from <a href="/gallery/lamecheez/">LameCheez</a></h3>

                                        <div class="preview-gallery hideonmobile">
                                            <div class="preview-gallery-container">
                                                <a href="/view/46683165/"><img class="preview-gallery-image" title="AVISHKA PARADISE" src="//t.furaffinity.net/46683165@200-1649367010.jpg"/></a>
                                            </div>
                                            <div class="preview-gallery-container">
                                                <a href="/view/46683148/"><img class="preview-gallery-image" title="AVISHKA PARADISE" src="//t.furaffinity.net/46683148@200-1649366944.jpg"/></a>
                                            </div>
                                            <div class="preview-gallery-container">
                                                <a href="/view/46683131/"><img class="preview-gallery-image" title="AVISHKA PARADISE" src="//t.furaffinity.net/46683131@200-1649366886.jpg"/></a>
                                            </div>
                                            <div class="preview-gallery-container">
                                                <a href="/view/46683103/"><img class="preview-gallery-image" title="AVISHKA PARADISE" src="//t.furaffinity.net/46683103@200-1649366768.jpg"/></a>
                                            </div>
                                            <div class="preview-gallery-container">
                                                <a href="/view/46683083/"><img class="preview-gallery-image" title="AVISHKA PARADISE" src="//t.furaffinity.net/46683083@200-1649366693.jpg"/></a>
                                            </div>
                                            <div class="preview-gallery-container">
                                                <a href="/view/46683065/"><img class="preview-gallery-image" title="AVISHKA PARADISE" src="//t.furaffinity.net/46683065@200-1649366626.jpg"/></a>
                                            </div>
                                        </div>
                                    </section>

                                    <section class="folder-list-container text">
                                        <h3>Listed in Folders</h3>
                                            <div>
                                                <a href="/gallery/lamecheez/folder/1110149/AVISHKA-PARADISE/" title="14 submissions" class="dotted">
                                                    <span>AVISHKA PARADISE</span>
                                                </a>
                                            </div>
                                    </section>
                                </div>

                                <div class="submission-content">
                                    <div class="aligncenter submission-area">
                                        <!-- submission preview -->
                                        <img id="submissionImg" class="imgresizer" title="Click to change the View" alt="AVISHKA PARADISE" data-fullview-src="//d.furaffinity.net/art/lamecheez/music/1649367062/1649367062.thumbnail.lamecheez_информатика.mp3.jpg" data-preview-src="//t.furaffinity.net/46683183@400-1649367062.jpg"  src="//d.furaffinity.net/art/lamecheez/music/1649367062/1649367062.thumbnail.lamecheez_информатика.mp3.jpg" style="cursor: pointer;" />
                
                                        <br />
                                        <div class="audio-player-container">
                                            <audio class="audio-player" src="//d.furaffinity.net/art/lamecheez/music/1649367062/1649367062.lamecheez_информатика.mp3" preload="metadata" controls>
                                                <embed class="audio-player" menu="false" type="application/x-shockwave-flash" src="/themes/beta/media/player.swf" flashvars="file=//d.furaffinity.net/art/lamecheez/music/1649367062/1649367062.lamecheez_информатика.mp3" allowscriptaccess="always" wmode="transparent" quality="high" />
                                            </audio>
                                        </div>
                                    </div>

                                    <div class="aligncenter auto_link hideonfull1 favorite-nav">
                                        <a class="button standard mobile-fix" href="/fav/46683183/?key=b3d836ea3258f7c05baeafa70a8ecd6aea69406b">+Fav</a>
                                        <a class="button standard mobile-fix" href="/gallery/lamecheez/" title="14 submissions">Main Gallery</a>
                                        <a class="button standard mobile-fix" href="//d.furaffinity.net/download/art/lamecheez/music/1649367062/1649367062.lamecheez_информатика.mp3">Download</a>
                                        <a class="button standard mobile-fix" href="/newpm/lamecheez/">Note</a>
                                        <a href="/view/46683165/" class="button standard mobile-fix">Next</a>
                                    </div>

                                    <section>
                                        <div class="section-header">
                                            <div class="submission-id-container">
                                                <div class="submission-id-avatar">
                                                    <a href="/user/lamecheez/"><img class="submission-user-icon floatleft avatar" src="//a.furaffinity.net/1649361143/lamecheez.gif"></a>
                                                </div>

                                                <div class="submission-id-sub-container">
                                                    <div class="submission-title">
                                                        <h2><p>AVISHKA PARADISE</p></h2>
                                                    </div>
                                                    By <a href="/user/lamecheez/"><strong>LameCheez</strong></a>,
                                                    <span class="hideontablet hideondesktop"><br> </span>
                                                    <span class="hideonmobile">posted </span>
                                                    <strong><span title="Apr 7, 2022 05:31 PM" class="popup_date">a day ago</span></strong>
                                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Lame cheez                    
                                                </div>
                                            </div>
                                        </div>
                                        <div class="section-body">
                                            <div class="submission-description user-submitted-links">
                                                HEWWOOO!!! My name is Lame Cheez. I'd love to join this community.<br />
                                                So, i am aploading my 1'st of April album (this is just a joke), as a furry. This is a revolution in me and hyperpop. I belive, that you wouldn't like to find my old creation's. Greetings from Russia! Free Ukraine!  PUTIN must (censored)<br />
                                                <br />
                                                My Soundcloud board:<br />
                                                <a href="https://soundcloud.com/lamecheez/sets/avishka-paradise?utm_source=clipboard&amp;utm_medium=text&amp;utm_campaign=social_sharing" title="https://soundcloud&#46;com/lamecheez/sets/avishka-paradise?utm_source=clipboard&amp;utm_medium=text&amp;utm_campaign=social_sharing" class="auto_link auto_link_shortened">https://soundcloud&#46;com/lamecheez/se.....social_sharing</a><br />
                                                <br />
                                                VOD            TROYVVARZ             PRESENTS:<br />
                                                <br />
                                                LAME CHEEZ - АВИШКА ПАРАДАЙЗ<br />
                                                PROD I1OPHO TE.E.AM.A AYY CHOPPA BANKOTVETOV EDGITY XEN<br />
                                                {{1147}} #VODSTROYVVARZ #AVISHKAPARADISE #D366TÁRÖVAANT3M | [FUXK SOUNDROP] NO COMMERCE<br />
                                                <br />
                                                Genres: Experimental Hip-Hop, Digicore, Surge, Minimalistic Trap, Detroit, Alternative Trap
                                            </div>
                                        </div>

                                        <!-- mobile view -->
                                        <div class="section-footer section-footer-padding hideonfull alignright">
                                            <div class="submission-stats-container">
                                                <div class="views">
                                                    <span class="font-large">69</span><br>
                                                    <span class="font-small highlight">Views</span>
                                                </div>
                
                                                <div class="favorites">
                                                    <span class="font-large">0</span><br>
                                                    <span class="font-small highlight">Favorites</span>
                                                </div>
                
                                                <div class="comments">
                                                    <span class="font-large">0</span><br>
                                                    <span class="font-small highlight">Comments</span>
                                                </div>
                
                                                <div class="rating">
                                                    <span class="font-large rating-box inline general"> General</span><br>
                                                    <span class="font-small highlight">Rating</span>
                                                </div>
                                            </div>
                                        </div>
                                    </section>

                                    <!-- mobile view -->
                                    <div class="hideonfull">
                                        <section class="stats-mobile">
                                            <div class="section-body info">
                                                <div><strong class="highlight">Category</strong> <span class="category-name">Music</span> / <span class="type-name">All</span></div>                    <div><strong class="highlight">Species</strong> <span>Unspecified / Any</span></div>                    <div><strong class="highlight">Gender</strong> <span>Any</span></div>                    <div><strong class="highlight">Size</strong> <span>120 x 120px</span></div>                </div>
                                        </section>
                                        <section class="tags-mobile">
                                            <div class="section-body">
                                                <span class="tags"><a href="/search/@keywords Experimental">Experimental</a></span>
                                                <span class="tags"><a href="/search/@keywords Hip-Hop">Hip-Hop</a></span>
                                                <span class="tags"><a href="/search/@keywords Digicore">Digicore</a></span>
                                                <span class="tags"><a href="/search/@keywords Surge">Surge</a></span>
                                                <span class="tags"><a href="/search/@keywords Minimalistic">Minimalistic</a></span>
                                                <span class="tags"><a href="/search/@keywords Trap">Trap</a></span>
                                                <span class="tags"><a href="/search/@keywords Detroit">Detroit</a></span>
                                                <span class="tags"><a href="/search/@keywords Alternative">Alternative</a></span>
                                                <span class="tags"><a href="/search/@keywords Trap">Trap</a></span>
                                            </div>
                                        </section>
                                        
                                        <section class="folders-mobile">
                                            <div class="section-body">
                                                <h3>Listed in Folders</h3>
                                                <div>
                                                    <a href="/gallery/lamecheez/folder/1110149/AVISHKA-PARADISE/" title="14 submissions" class="dotted">
                                                        <span>AVISHKA PARADISE</span>
                                                    </a>
                                                </div>
                                            </div>
                                        </section>
                                    </div>
                
                                    <div class="comments-list">
                                        <div id="comments-submission"></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- /<div id="site-content"> -->
                </div>
            </body>
            </html>
        """.trimIndent()

    @Test
    fun constructor_parsesAudioPost() {
        val details = PagePostDetails(EXAMPLE_BODY_AUDIO)

        assertEquals(details.Artist, "LameCheez")
        assertEquals(details.Comments.size, 0)
        assertEquals(details.ContentUrl, "https://d.furaffinity.net/art/lamecheez/music/1649367062/1649367062.lamecheez_информатика.mp3")
        assertEquals(details.Description.substring(0, 33), "HEWWOOO!!! My name is Lame Cheez.")
        assertEquals(details.FavoriteKey, "b3d836ea3258f7c05baeafa70a8ecd6aea69406b")
        assertEquals(details.HasFavorited, false)
        assertEquals(details.Kind, PostKind.Music)
        assertEquals(details.Rating, AgeRating.General)
        assertEquals(details.Tags.size, 8)
        assertEquals(details.ThumbnailUrl, "https://d.furaffinity.net/art/lamecheez/music/1649367062/1649367062.thumbnail.lamecheez_информатика.mp3.jpg")
        assertEquals(details.Title, "AVISHKA PARADISE")
        assertEquals(details.TotalFavorites, 0)
        assertEquals(details.TotalViews, 69)
        assertEquals(details.UploadDate, DateFormatter.createDate(2022, 4, 7, 17, 31))
    }

    @Test
    fun constructor_parsesImagePost() {
        var details : PagePostDetails = PagePostDetails(EXAMPLE_BODY_IMAGE)

        assertEquals(details.Artist, "nimfaeya")
        assertEquals(details.Comments.size, 0)
        assertEquals(details.ContentUrl, null)
        //assertEquals(details.Description, "")
        assertEquals(details.FavoriteKey, "263aecbeb29aed6793c16f7748f9f07a89a6bda1")
        assertEquals(details.HasFavorited, false)
        assertEquals(details.Kind, PostKind.Image)
        assertEquals(details.Rating, AgeRating.General)
        assertEquals(details.Tags.size, 6)
        assertEquals(details.ThumbnailUrl, "https://d.furaffinity.net/art/nimfaeya/1654462809/1654462809.nimfaeya_2016-07-26.png")
        assertEquals(details.Title, "The Black Tea Geisha VII")
        assertEquals(details.TotalFavorites, 0)
        assertEquals(details.TotalViews, 8)
        assertEquals(details.UploadDate, DateFormatter.createDate(2022, 6, 5, 17, 0))
    }


}
