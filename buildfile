
VERSION_NUMBER = "0.6.1-SNAPSHOT"
COPYRIGHT = 'Copyright \u00A9 2009-2014  Hex Project'

# Assist my local build environment where there are multiple JREs.
if ENV['JAVA_HOME_7']
  ENV['JAVA_HOME'] = ENV['JAVA_HOME_7']
end

repositories.remote << 'https://oss.sonatype.org/content/groups/public'
repositories.remote << 'http://www.ibiblio.org/maven2/'
repositories.remote << 'http://mirrors.ibiblio.org/pub/mirrors/maven2/'

if ENV['DEPLOY_USER']
  repositories.release_to = { url: ( VERSION_NUMBER =~ /SNAPSHOT/ ?
                                     'https://oss.sonatype.org/content/repositories/snapshots' :
                                     'https://oss.sonatype.org/service/local/staging/deploy/maven2' ),
                              username: ENV['DEPLOY_USER'],
                              password: ENV['DEPLOY_PASS'] }
end

INTELLIJ_ANNOTATIONS  =   artifact('com.intellij:annotations:jar:12.0')
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

if VERSION_NUMBER !~ /SNAPSHOT/
  require 'buildr/gpg'
end

require 'buildr/custom_pom'

desc 'Main project'
define 'hex' do
  project.version = VERSION_NUMBER
  project.group = 'org.trypticon.hex'

  pom.add_lgpl_v3_license
  pom.add_github_project('trejkaz/hex-components')
  pom.add_developer('trejkaz', 'Trejkaz', 'trejkaz@trypticon.org')

  manifest['Copyright'] = COPYRIGHT
  compile.options.source = compile.options.target = '1.7'

  desc 'Hex Utilities'
  define 'util' do
    pom.description = 'Utilities used by other hex components'
    compile.with INTELLIJ_ANNOTATIONS
    package :jar
    package :sources
    package :javadoc
  end

  desc 'Hex Binary API'
  define 'binary' do
    pom.description = 'API for abstraction of things containing binary data'
    compile.with projects('util')
    compile.with INTELLIJ_ANNOTATIONS
    package :jar
    package :sources
    package :javadoc
  end

  desc 'Hex Interpreter API'
  define 'interpreter' do
    pom.description = 'API for interpreting runs of binary as values humans can understand'
    compile.with projects('binary', 'util')
    compile.with INTELLIJ_ANNOTATIONS
    package :jar
    package :sources
    package :javadoc
  end

  desc 'Hex Annotations API'
  define 'anno' do
    pom.description = 'API for annotating runs of binary with arbitrary annotations'
    compile.with projects('binary', 'interpreter', 'util')
    compile.with INTELLIJ_ANNOTATIONS
    package :jar
    package :sources
    package :javadoc
  end

  desc 'Hex Viewer'
  define 'viewer' do
    pom.description = 'Hex viewer and related components'
    compile.with projects('anno', 'binary', 'interpreter', 'util')
    compile.with INTELLIJ_ANNOTATIONS
    compile.with SWINGX
    package :jar
    package :sources
    package :javadoc
  end

  desc 'Examples'
  define 'examples' do
    pom.description = 'Examples'
    compile.with projects('anno', 'binary', 'interpreter', 'util', 'viewer')
    compile.with INTELLIJ_ANNOTATIONS
    package :jar
    package :sources
    package :javadoc
  end

  binaries_id = "#{id}-components-#{version}"
  package(:zip, :file => _("target/#{binaries_id}.zip")).path(binaries_id).tap do |path|
    path.include 'COPYING', 'COPYING.LESSER', 'CHANGELOG', 'README.markdown'

    %w{anno binary interpreter util viewer}.each do |p|
      path.include project(p).package(:jar)
    end

    path.include 'examples'
    path.exclude 'examples/examples.iml', 'examples/target', 'examples/reports'
  end

end
