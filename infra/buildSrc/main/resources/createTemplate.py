import sys
import os
import ConfigParser
    
parsedProperties = {}

def loadProperties(propertyFile):
    """Parses properties INI file"""
    props = ConfigParser.ConfigParser()
    props.read(PROPERTY_FILE)
    parsedProperties['sourceTemplate'] = props.get('common','domain.sourceTemplate')
    parsedProperties['customTemplate'] = props.get('common','domain.customTemplate')
    parsedProperties['password']       = props.get('common','domain.password')    
    return parsedProperties


def configureTemplate(properties):
    """Loads source domain template and customize it"""
    if not os.path.isfile(properties['sourceTemplate']):
        print "Cannot find source template:\n%s" % properties['sourceTemplate']
        raise IOError
    readTemplate(properties['sourceTemplate'])
    # user 'weblogic' is hardcoded on wls.jar (base domain template)
    cd("/Security/base_domain/User/weblogic")
    cmo.setPassword(properties['password'])


def save():
    """Writes a domain template file to the file system, overwriting if already exists"""
    customTemplate = parsedProperties['customTemplate']
    if os.path.exists(customTemplate):
        os.remove(customTemplate)
    writeTemplate(customTemplate)
    closeTemplate()
    print "%s domain template created" % os.path.join(os.getcwd(), customTemplate)
    return True


##Main Starts
PROPERTY_FILE = './createTemplate.properties'
try:
    PROPERTY_FILE = sys.argv[1]
except:
    print "No parameter provided for property file. Assumes to find it in working dir."

parsedConfig = loadProperties(PROPERTY_FILE)
try:
    configureTemplate(parsedConfig)
    save()
    exit()
except:
    print "Error trying to create template file:\n%s\nPlease check path and permissions" % os.path.join(os.getcwd(), parsedConfig['customTemplate'])
    exit(exitcode=1)
