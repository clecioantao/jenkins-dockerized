/*
 * Create the initial admin user
 * Inspiration from, https://github.com/geerlingguy/ansible-role-jenkins/issues/50
*/

import jenkins.model.*
import hudson.security.*

def env = System.getenv()

def instance = Jenkins.getInstance()

println "--> Creating the local admin user....."

def hudsonRealm = new HudsonPrivateSecurityRealm(false)
hudsonRealm.createAccount(env.JENKINS_ADMIN_USER, env.JENKINS_ADMIN_PASS)
instance.setSecurityRealm(hudsonRealm)

def strategy = new FullControlOnceLoggedInAuthorizationStrategy()
strategy.setAllowAnonymousRead(false)
instance.setAuthorizationStrategy(strategy)

instance.save()
