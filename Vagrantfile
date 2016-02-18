Vagrant.configure(2) do |config|
  config.vm.define "linux" do |linux|
    linux.vm.box = "devopslabs/centos-7.0-x86_64"
    linux.vm.network "forwarded_port", guest: 7001, host: 7011
    linux.vm.provision "ansible" do |ansible|
      ansible.playbook = "infra/vagrant/playbook.yml"
    end
  end

  # config.vm.define "windows" do |windows|
  #   # so far, build one yourself with packer +
  #   # https://github.com/boxcutter/windows
  #   windows.vm.box = "windows7"
  #   windows.vm.network "forwarded_port", guest: 7001, host: 7111
  #   # Ansible to Windows conn is broken on Ansible 2.0-2.1
  #   # windows.vm.provision "ansible" do |ansible|
  #   #   ansible.playbook = "infra/vagrant/playbook_win.yml"
  #   #   ansible.verbose = "vvv"
  #   # end
  # end

  config.vm.provider "virtualbox" do |v|
    v.memory = 2048
    v.cpus = 2
  end
end
