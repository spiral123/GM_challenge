# GM Coding Challenge

## Approach

- Establish new github repo for project
- Create this readme
- Manually check the query url to ensure required data is returned and see if anything else useful is present (e.g. image links)
- In Android Studio create new Project from Activity only template
- Link new AS project to github repo
    - Copy over `.gitignore` from another project
- Setup DependencyInjection, MVI framework
    - Setup dependencies for coroutines and dagger
    - Setup initial injections: OkHttp, Retrofit, NetworkArtistFetch, ArtistViewModel etc.
- Backend Development
    - Write NetworkArtistFetch.kt
    - Write ArtistViewModel.kt
    - Write unit tests for NetworkArtistFetch.kt and check coverage
    - Write unit tests for ArtistViewModel and check coverage
- UI Development
    - Add ViewBinding to MainActivity
    - Start on layout: EditText and Search Button
    - Work on EditText clear clicking
    - Work on handling button click and initiate intent
    





## Data
- Test Url: https://itunes.apple.com/search?term=bob%20dylan

- result:

```javascript
object.  {2}
    resultCount	:	50
    results		[50]
      0   {34}
        wrapperType	:	track
        kind	:	song
        artistId	:	462006
        collectionId	:	251001680
        trackId	:	251002654
        artistName	:	Bob Dylan & Johnny Cash
        collectionName	:	The Essential Johnny Cash
        trackName	:	Girl from the North Country
        collectionCensoredName	:	The Essential Johnny Cash
        trackCensoredName	:	Girl from the North Country
        collectionArtistId	:	70936
        collectionArtistName	:	Johnny Cash
        collectionArtistViewUrl	:	https://music.apple.com/us/artist/johnny-cash/70936?uo=4
        artistViewUrl	:	https://music.apple.com/us/artist/bob-dylan/462006?uo=4
        collectionViewUrl	:	https://music.apple.com/us/album/girl-from-the-north-country/251001680?i=251002654&uo=4
        trackViewUrl	:	https://music.apple.com/us/album/girl-from-the-north-country/251001680?i=251002654&uo=4
        previewUrl	:	https://audio-ssl.itunes.apple.com/itunes-assets/Music/ee/c1/8f/mzi.ghrtrjno.aac.p.m4a
        artworkUrl30	:	https://is2-ssl.mzstatic.com/image/thumb/Music3/v4/13/ae/73/13ae735e-33d0-1480-f51b-4150d4a45696/source/30x30bb.jpg
        artworkUrl60	:	https://is2-ssl.mzstatic.com/image/thumb/Music3/v4/13/ae/73/13ae735e-33d0-1480-f51b-4150d4a45696/source/60x60bb.jpg
        artworkUrl100	:	https://is2-ssl.mzstatic.com/image/thumb/Music3/v4/13/ae/73/13ae735e-33d0-1480-f51b-4150d4a45696/source/100x100bb.jpg
        collectionPrice	:	14.99
        trackPrice	:	1.29
        releaseDate	:	1969-02-01T12:00:00Z
        collectionExplicitness	:	notExplicit
        trackExplicitness	:	notExplicit
        discCount	:	2
        discNumber	:	2
        trackCount	:	18
        trackNumber	:	6
        trackTimeMillis	:	222267
        country	:	USA
        currency	:	USD
        primaryGenreName	:	Singer/Songwriter
        isStreamable	:	true
      1   {31}
      2   {31}
      3   {31}
      ...
      50  {31}
```
