
VERSION_NUMBER = "0.8.1-SNAPSHOT"
COPYRIGHT = 'Copyright \u00A9 2009-2014,2016-2017  Hex Project'

repositories.remote << 'https://oss.sonatype.org/content/groups/public'
repositories.remote << 'http://central.maven.org/maven2/'
repositories.remote << 'http://www.ibiblio.org/maven2/'
repositories.remote << 'http://mirrors.ibiblio.org/pub/mirrors/maven2/'

if VERSION_NUMBER =~ /SNAPSHOT/
  if ENV['DEPLOY_USER']
    repositories.release_to = { url: 'https://oss.sonatype.org/content/repositories/snapshots',
                                username: ENV['DEPLOY_USER'],
                                password: ENV['DEPLOY_PASS'] }
  end
  if ENV['GPG_USER']
    require 'buildr/gpg'
  end
else
  repositories.release_to = { url: 'https://oss.sonatype.org/service/local/staging/deploy/maven2',
                              username: ENV['DEPLOY_USER'],
                              password: ENV['DEPLOY_PASS'] }
  require 'buildr/gpg'
end

JAVAX_ANNOTATIONS     =   artifact('com.google.code.findbugs:jsr305:jar:3.0.2')
ICU4J                 = [ artifact('com.ibm.icu:icu4j:jar:59.1'),
                          artifact('com.ibm.icu:icu4j-charsets:jar:59.1') ]
SWINGX                = [ artifact('org.swinglabs.swingx:swingx-action:jar:1.6.6-SNAPSHOT'),
                          artifact('org.swinglabs.swingx:swingx-common:jar:1.6.6-SNAPSHOT'),
                          artifact('org.swinglabs.swingx:swingx-core:jar:1.6.6-SNAPSHOT'),
                          artifact('org.swinglabs.swingx:swingx-painters:jar:1.6.6-SNAPSHOT'),
                          artifact('org.swinglabs.swingx:swingx-plaf:jar:1.6.6-SNAPSHOT') ]

download artifact('org.swinglabs.swingx:swingx-action:jar:1.6.6-SNAPSHOT') =>
  'https://github.com/trejkaz/swingx/releases/download/v1.6.6-SNAPSHOT.2014.06.15/swingx-action-1.6.6-SNAPSHOT.jar'
download artifact('org.swinglabs.swingx:swingx-common:jar:1.6.6-SNAPSHOT') =>
  'https://github.com/trejkaz/swingx/releases/download/v1.6.6-SNAPSHOT.2014.06.15/swingx-common-1.6.6-SNAPSHOT.jar'
download artifact('org.swinglabs.swingx:swingx-core:jar:1.6.6-SNAPSHOT') =>
  'https://github.com/trejkaz/swingx/releases/download/v1.6.6-SNAPSHOT.2014.06.15/swingx-core-1.6.6-SNAPSHOT.jar'
download artifact('org.swinglabs.swingx:swingx-painters:jar:1.6.6-SNAPSHOT') =>
  'https://github.com/trejkaz/swingx/releases/download/v1.6.6-SNAPSHOT.2014.06.15/swingx-painters-1.6.6-SNAPSHOT.jar'
download artifact('org.swinglabs.swingx:swingx-plaf:jar:1.6.6-SNAPSHOT') =>
  'https://github.com/trejkaz/swingx/releases/download/v1.6.6-SNAPSHOT.2014.06.15/swingx-plaf-1.6.6-SNAPSHOT.jar'
download artifact('com.ibm.icu:icu4j-charsets:jar:59.1') =>
  'http://download.icu-project.org/files/icu4j/59.1/icu4j-charset-59_1.jar'


require 'buildr/custom_pom'

desc 'Main project'
define 'hex' do
  project.version = VERSION_NUMBER
  project.group = 'org.trypticon.hex'

  pom.add_lgpl_v3_license
  pom.add_github_project('trejkaz/hex-components')
  pom.add_developer('trejkaz', 'Trejkaz', 'trejkaz@trypticon.org')

  manifest['Copyright'] = COPYRIGHT
  compile.options.source = compile.options.target = '1.8'

  desc 'Hex Utilities'
  define 'util' do
    pom.description = 'Utilities used by other hex components'
    compile.with JAVAX_ANNOTATIONS
    compile.with ICU4J
    compile.with SWINGX
    package :jar
    package :sources
    package :javadoc
  end

  desc 'Hex Binary API'
  define 'binary' do
    pom.description = 'API for abstraction of things containing binary data'
    compile.with project('util').package(:jar)
    compile.with JAVAX_ANNOTATIONS
    package :jar
    package :sources
    package :javadoc
  end

  desc 'Hex Interpreter API'
  define 'interpreter' do
    pom.description = 'API for interpreting runs of binary as values humans can understand'
    compile.with project('binary').package(:jar)
    compile.with project('util').package(:jar)
    compile.with JAVAX_ANNOTATIONS
    package :jar
    package :sources
    package :javadoc
  end

  desc 'Hex Annotations API'
  define 'anno' do
    pom.description = 'API for annotating runs of binary with arbitrary annotations'
    compile.with project('binary').package(:jar)
    compile.with project('interpreter').package(:jar)
    compile.with project('util').package(:jar)
    compile.with JAVAX_ANNOTATIONS
    package :jar
    package :sources
    package :javadoc
  end

  desc 'Hex Viewer'
  define 'viewer' do
    pom.description = 'Hex viewer and related components'
    compile.with project('anno').package(:jar)
    compile.with project('binary').package(:jar)
    compile.with project('interpreter').package(:jar)
    compile.with project('util').package(:jar)
    compile.with JAVAX_ANNOTATIONS
    compile.with SWINGX
    package :jar
    package :sources
    package :javadoc
  end

  desc 'Examples'
  define 'examples' do
    pom.description = 'Examples'
    compile.with project('anno').package(:jar)
    compile.with project('binary').package(:jar)
    compile.with project('interpreter').package(:jar)
    compile.with project('util').package(:jar)
    compile.with project('viewer').package(:jar)
    compile.with JAVAX_ANNOTATIONS
    package :jar
    package :sources
    package :javadoc
  end

  package(:zip, :id => "#{id}-components").path("#{id}-components-#{version}").tap do |path|
    path.include 'COPYING', 'COPYING.LESSER', 'CHANGELOG', 'README.markdown'

    %w{anno binary interpreter util viewer}.each do |p|
      path.include project(p).package(:jar)
    end

    path.include 'examples'
    path.exclude 'examples/examples.iml', 'examples/target', 'examples/reports'
  end

end
