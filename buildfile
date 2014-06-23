
VERSION_NUMBER = "0.6.1-SNAPSHOT"
COPYRIGHT = 'Copyright \u00A9 2009-2014  Hex Project'

ENV['JAVA_HOME'] = ENV['JAVA_HOME_7']

repositories.remote << 'https://oss.sonatype.org/content/groups/public'
repositories.remote << 'http://www.ibiblio.org/maven2/'
repositories.remote << 'http://mirrors.ibiblio.org/pub/mirrors/maven2/'


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


desc 'Main project'
define 'hex' do
  project.version = VERSION_NUMBER
  project.group = 'org.trypticon.hex'
  manifest['Copyright'] = COPYRIGHT
  compile.options.source = compile.options.target = '1.7'

  desc 'Hex Utilities'
  define 'util' do
    package :jar
  end

  desc 'Hex Binary API'
  define 'binary' do
    compile.with projects('util')
    package :jar
  end

  desc 'Hex Interpreter API'
  define 'interpreter' do
    compile.with projects('binary', 'util')
    package :jar
  end

  desc 'Hex Annotations API'
  define 'anno' do
    compile.with projects('binary', 'interpreter', 'util')
    package :jar
  end

  desc 'Hex Viewer'
  define 'viewer' do
    compile.with projects('anno', 'binary', 'interpreter', 'util')
    compile.with SWINGX
    package :jar
  end

  desc 'Examples'
  define 'examples' do
    compile.with projects('anno', 'binary', 'interpreter', 'util', 'viewer')
    package :jar
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
