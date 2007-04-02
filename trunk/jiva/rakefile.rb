task :default => [:run_samples]

#build vars
jar_name = 'jeeva.jar'
build_dir = 'build'
dist_dir = 'dist'
src_dir = 'src'
thirdparty_dir = 'thirdparty'
dist_thirdparty = 'dist/lib/thirdparty'
dist_zofer = 'dist/lib/zofer'
dist_lib = 'dist/lib'
dist_bin = 'dist/bin'

# dir creation tasks
directory build_dir
directory dist_thirdparty
directory dist_zofer
directory dist_bin


task :init_dirs => [build_dir, dist_thirdparty, dist_zofer, dist_bin] do
end

task :compile => [:init_dirs] do
  p 'Compling Java Code'
  javac src_dir, build_dir, thirdparty_dir
  p 'Done...'
end

task :jar => [:compile] do
  jar dist_zofer + '/' + jar_name, build_dir
  p 'Copying thirdparty jars'
  cp_r Dir.glob(thirdparty_dir+'/*.jar'), dist_thirdparty, :verbose => true
end

task :dist => [:jar] do
# The copying does not work here! Moved to :jar task for now
#  p 'Copying thirdparty jars'
#  cp_r Dir.glob(thirdparty_dir+'/*.jar'), dist_thirdparty, :verbose => true
end

task :run_samples => [:dist] do
  java 'net.zofer.jeeva.samples.customscape.Main', dist_lib
end

task :clean do
  rm_rf build_dir
  rm_rf dist_dir
end


def java(main_class, jar_base)
  classpath = FileList.new("#{jar_base}/**/*.jar")
  print "Running: java -cp \"#{classpath.join(';')}\" #{main_class}"
  system "java -Dlog4j.configuration=src/log4j.properties -cp \".;#{classpath.join(';')}\" #{main_class}"
end

def jar(jarfile, tojar_dir)
  p "Building jar: jar cf #{jarfile} -C #{tojar_dir} ."
  system "jar cf #{jarfile} -C #{tojar_dir} ."
  p 'Jar building done'
end


def javac(src, classes, jarbase)
  jar_files = FileList.new("#{jarbase}/**/*.jar")
  java_files = FileList.new("#{src}/**/*.java")
  subs = {'.java'=>'.class', src=>classes}
  class_files = java_files.gsub(/(\.java$)|(^src)/) {|match| subs[match]}
  to_build = []
  for index in 0...java_files.size 
    unless uptodate?(class_files[index], java_files[index]) 
      to_build << java_files[index]
    end
  end
  num_files = to_build.size
  if num_files == 0
    p "Nothing to compile"
  else
    p "Compiling #{num_files} java files"
#    p "javac -cp #{jar_files.join(';')} #{to_build.join(' ')} -d classes"
    system "javac -cp #{jar_files.join(';')} #{to_build.join(' ')} -d #{classes}"
  end
end

class FileList
  def gsub(pat, &block)
    inject(FileList.new) { |res, fn| res << fn.gsub(pat,&block) }
  end
end
