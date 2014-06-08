
NAME = 'Hex Components'
VERSION_NUMBER = '0.5'
COPYRIGHT = 'Copyright (C) 2009-2014  Trejkaz, Hex Project'

ENV['JAVA_HOME'] = ENV['JAVA_HOME_7']

repositories.remote << 'http://www.ibiblio.org/maven2/'
repositories.remote << 'http://mirrors.ibiblio.org/pub/mirrors/maven2/'

desc 'Main project'
define 'hex-components' do
  project.version = VERSION_NUMBER
  project.group = 'hex-components'
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

  sources_id = "#{id}-sources-#{version}"
  package(:zip, :file => _("target/#{sources_id}.zip")).path(sources_id).tap do |path|
    path.include '*'
    path.exclude '**/target', '**/reports'
    path.include '.idea'
    path.exclude '.idea/workspace.xml', '.idea/out', '.idea/dictionaries'
  end

  binaries_id = "#{id}-binaries-#{version}"
  package(:zip, :file => _("target/#{binaries_id}.zip")).path(binaries_id).tap do |path|
    path.include 'COPYING', 'COPYING.LESSER', 'CHANGELOG', 'README.markdown'
    path.include project('anno').packages
    path.include project('binary').packages
    path.include project('interpreter').packages
    path.include project('util').packages
    path.include project('viewer').packages
    path.include 'examples'
    path.exclude 'examples/examples.iml'
    path.exclude '**/target', '**/reports'
  end

end
