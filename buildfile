
NAME = 'Hex Components'
VERSION_NUMBER = '0.6'
COPYRIGHT = '\u00A9 2009-2014  Hex Project'

ENV['JAVA_HOME'] = ENV['JAVA_HOME_7']

repositories.remote << 'http://www.ibiblio.org/maven2/'
repositories.remote << 'http://mirrors.ibiblio.org/pub/mirrors/maven2/'

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
